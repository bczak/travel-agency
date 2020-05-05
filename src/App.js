import React from 'react'
import API from './api'
import 'react-date-range/dist/styles.css' // main style file
import 'react-date-range/dist/theme/default.css' // theme css file
import {DateRangePicker} from 'react-date-range'

import {
	AppBar,
	Paper,
	Typography,
	Button,
	Toolbar,
	Slider,
	TextField,
	Switch
} from '@material-ui/core'
import Autocomplete from '@material-ui/lab/Autocomplete'
import Backdrop from '@material-ui/core/Backdrop'
import CircularProgress from '@material-ui/core/CircularProgress'
import {CardItem} from './components/CardItem'
import FormControlLabel from '@material-ui/core/FormControlLabel'

export function getDate(date) {
	const ye = new Intl.DateTimeFormat('en', {year: 'numeric'}).format(date)
	const mo = new Intl.DateTimeFormat('en', {month: '2-digit'}).format(date)
	const da = new Intl.DateTimeFormat('en', {day: '2-digit'}).format(date)
	return `${da}.${mo}.${ye}`
}

const SORT_TYPES = {
	TITLE: 'TITLE',
	DURATION: 'DURATION',
	LOW_PRICE: 'LOW PRICE',
	HIGH_PRICE: 'HIGH PRICE',
	NEWEST: 'NEWEST',
	LATEST: 'LATEST'
}

class App extends React.Component {

	getTrips = async (by = 'PRICE', order = 'ASCENDING') => {
		try {
			const trips = await API.getTripsApi(by, order)
			if (Array.isArray(trips)) {
				this.setState({trips})
			}
		} catch (error) {
			console.log({error})
		}
	}
	allTags = []

	async componentDidMount() {
		const tag = await API.getAllTags()
		const cnt = await API.getAllCountries()
		this.setState({...this.state, tags: tag, countries: cnt})
	}

	constructor() {
		super()
		this.state = {
			tags: [],
			selectedTags: [],
			selectedCountries: [],
			isAllTags: true,
			isAllCountries: true,
			countries: [],
			value: [1000, 2000],
			resLength: 0,
			cards: [],
			sort: SORT_TYPES.TITLE,
			open: false,
			expanded: false,
			offset: 10,
			duration: [0, 90],
			selectionRange: {
				startDate: new Date(),
				endDate: new Date(),
				key: 'selection'
			}
		}
	}

	handleLimit = (value) => {
		this.setState({limit: value})
		this.search(value)
	}

	sortBy = (param) => {
		this.search(param)
	}

	handleChange = (event, newValue) => {
		this.setState({...this.state, value: newValue})
	}
	handleDuration = (event, newValue) => {
		this.setState({...this.state, duration: newValue})
	}

	handleSelect = (ranges) => {
		this.setState({
			...this.state,
			selectionRange: {
				...ranges.selection,
				key: this.state.selectionRange.key
			}
		})
	}

	getSortedCards = (cards, sort_type) => {
		//filter price
		cards = cards.filter((a) => {
			return a.price >= this.state.value[0] && a.price <= this.state.value[1]
		})
		//filter date
		cards = cards.filter(a => {
			return (new Date(a.startDate) >= this.state.selectionRange.startDate && new Date(a.endDate) <= this.state.selectionRange.endDate) || (this.state.selectionRange.endDate.toDateString() === this.state.selectionRange.startDate.toDateString());
		})
		if (sort_type === SORT_TYPES.TITLE) {
			return cards.sort((a, b) => {
				const nameA = a.name.toLowerCase()
				const nameB = b.name.toLowerCase()
				if (nameA < nameB) return -1
				if (nameA > nameB) return 1
				return 0
			})
		} else if (sort_type === SORT_TYPES.DURATION) {
			return cards.sort((a, b) => {
				let durationA = Math.abs(new Date(a.startDate) - new Date(a.endDate))
				let durationB = Math.abs(new Date(b.startDate) - new Date(b.endDate))
				return durationA - durationB
			})
		} else if (sort_type === SORT_TYPES.LOW_PRICE) {
			return cards.sort((a, b) => a.price - b.price)
		} else if (sort_type === SORT_TYPES.HIGH_PRICE) {
			return cards.sort((a, b) => b.price - a.price)
		} else if (sort_type === SORT_TYPES.NEWEST) {
			return cards.sort((a, b) => {
				let A = new Date(a.startDate)
				let B = new Date(b.startDate)
				return A - B
			})
		} else if (sort_type === SORT_TYPES.LATEST) {
			return cards.sort((a, b) => {
				let A = new Date(a.startDate)
				let B = new Date(b.startDate)
				return B - A
			})
		} else {
			return cards
		}
	}

	search = async (sort_type = SORT_TYPES.TITLE) => {
		this.setState({...this.state, sort: sort_type, open: true})

		const cards = await API.getTrips(
			this.state.selectedTags.map((e) => e.title),
			this.state.isAllTags,
			this.state.selectedCountries.map((e) => e.title),
			this.state.isAllCountries,
			this.state.duration//min and max
		)

		if (cards.length === 0) alert('No results')

		this.setState({
			...this.state,
			cards: this.getSortedCards(cards, sort_type),
			resLength: cards.length,
			open: false
		})
	}

	render() {
		function valuetext(value) {
			return `${value}$`
		}

		return (
			<div id="App">
				<AppBar className={'root'}>
					<Toolbar>
						<Typography variant="h6" className={'title'}>
							Travel Agency
						</Typography>
					</Toolbar>
				</AppBar>
				<Paper elevation={3} variant={'outlined'} className={'main'}>
					<div className="filter">
						<span id={'filter'}>Filter</span>
						<span className={'select-data'}>Select data range:</span>
						<div className={'date'}>
							<DateRangePicker
								editableDateInputs={true}
								ranges={[this.state.selectionRange]}
								onChange={this.handleSelect}
								moveRangeOnFirstSelection={false}
							/>
							<div className="params">
								<Autocomplete
									multiple
									id="tags"
									options={this.state.tags}
									onChange={(k, v) => {
										this.setState({...this.state, selectedTags: v})
									}}
									getOptionLabel={(option) => option.title}
									renderInput={(params) => (
										<TextField
											{...params}
											variant="outlined"
											label="Select tags"
											placeholder="Tags"
										/>
									)}
								/>
								<FormControlLabel
									control={<Switch
										onChange={(value) => {
											this.setState({...this.state, isAllTags: !this.state.isAllTags})
										}}
										value={this.state.isAllTags}
									/>
									}
									label={this.state.isAllTags ? 'Any match' : 'All match'}
								/>

								<span className={'spacer'}/>
								<Autocomplete
									multiple
									id="cntrs-standard"
									options={this.state.countries}
									onChange={(k, v) => {
										this.setState({...this.state, selectedCountries: v})
									}}
									getOptionLabel={(option) => option.title}
									renderInput={(params) => (
										<TextField
											{...params}
											variant="outlined"
											label="Select countries"
											placeholder="Countries"
										/>
									)}
								/>
								<FormControlLabel
									control={
										<Switch
											onChange={(value) => {
												this.setState({
													...this.state,
													isAllCountries: !this.state.isAllCountries
												})
											}}
											value={this.state.isAllCountries}
										/>}
									label={this.state.isAllCountries ? 'Any match' : 'All match'}

								/>

								<span className="spacer"/>
								<div>
									<Typography id="range-slider" gutterBottom>
										Price range
									</Typography>
									<Slider
										className={'slider'}
										// marks={true}
										value={this.state.value}
										min={0}
										max={5000}
										onChange={this.handleChange}
										aria-labelledby="range-slider"
										getAriaValueText={valuetext}
										valueLabelDisplay="on"
									/>
									<Typography>
										Price from {this.state.value[0]}$ to {this.state.value[1]}$
									</Typography>
								</div>
								<span className="spacer"></span>
								<div>
									<Typography id="range-slider" gutterBottom>
										Duration range
									</Typography>
									<Slider
										className={'slider'}
										// marks={true}
										value={this.state.duration}
										min={0}
										max={90}
										onChange={this.handleDuration}
										aria-labelledby="range-slider"
										getAriaValueText={valuetext}
										valueLabelDisplay="on"
									/>
									<Typography>
										Duration from {this.state.duration[0]} days to {this.state.duration[1]} days
									</Typography>
								</div>

								{/*<ChipInput*/}
								{/*	value={tags}*/}
								{/*	onAdd={(chip) => handleAddChip(chip)}*/}
								{/*	onDelete={(chip, index) => handleDeleteChip(chip, index)}*/}
								{/*	dataSource={allTags}*/}
								{/*	helperText={'Select Tags'}*/}

								{/*/>*/}
							</div>
						</div>
						<Button
							className={'button-search'}
							variant={'outlined'}
							color={'primary'}
							onClick={this.search}
						>
							Search
						</Button>
					</div>
				</Paper>
				{this.state.cards.length > 0 && (
					<>
						<Paper className={'reminder'} variant={'outlined'} elevation={2}>
							<Typography variant={'h4'}>
								Do you want to subscribe to this filter?
							</Typography>
							<TextField
								variant={'outlined'}
								className={'subs-input'}
								placeholder={'Enter your email'}
								type={'mail'}
							/>
							<Button variant={'outlined'} color={'primary'} className={'subs'}>
								Subscribe
							</Button>
						</Paper>
						<Paper className={'result'} variant={'outlined'} elevation={2}>
							<Paper className={'panel'} variant={'outlined'} elevation={3}>
								<div className="button-group">
									<Button>Sort By:</Button>
									<Button
										onClick={() => this.sortBy(SORT_TYPES.TITLE)}
										disableElevation
										variant={
											this.state.sort === SORT_TYPES.TITLE
												? 'contained'
												: 'outlined'
										}
										color={'primary'}
									>
										Title
									</Button>
									<Button
										onClick={() => this.sortBy(SORT_TYPES.DURATION)}
										disableElevation
										variant={
											this.state.sort === SORT_TYPES.DURATION
												? 'contained'
												: 'outlined'
										}
										color={'primary'}
									>
										Duration
									</Button>
									<Button
										onClick={() => this.sortBy(SORT_TYPES.LOW_PRICE)}
										disableElevation
										variant={
											this.state.sort === SORT_TYPES.LOW_PRICE
												? 'contained'
												: 'outlined'
										}
										color={'primary'}
									>
										Low Price
									</Button>
									<Button
										onClick={() => this.sortBy(SORT_TYPES.HIGH_PRICE)}
										disableElevation
										variant={
											this.state.sort === SORT_TYPES.HIGH_PRICE
												? 'contained'
												: 'outlined'
										}
										color={'primary'}
									>
										High Price
									</Button>
									<Button
										onClick={() => this.sortBy(SORT_TYPES.NEWEST)}
										disableElevation
										variant={
											this.state.sort === SORT_TYPES.NEWEST
												? 'contained'
												: 'outlined'
										}
										color={'primary'}
									>
										Newest
									</Button>
									<Button
										onClick={() => this.sortBy(SORT_TYPES.LATEST)}
										disableElevation
										variant={
											this.state.sort === SORT_TYPES.LATEST
												? 'contained'
												: 'outlined'
										}
										color={'primary'}
									>
										Latest
									</Button>
								</div>
							</Paper>
							<Paper className={'res'} elevation={3} variant={'outlined'}>
								<div className="resultText">{this.state.resLength} results</div>
							</Paper>

							{this.state.cards.slice(0, this.state.offset).map((card) => (
								<CardItem key={card.id} card={card}/>
							))}
							<div className="showMoreButton">
								<Button
									variant="outlined"
									onClick={() => {
										this.setState({offset: this.state.offset + 10})
									}}
								>
									Show more
								</Button>
							</div>
						</Paper>
					</>
				)}
				<Backdrop open={this.state.open} className={'loader'}>
					<CircularProgress color="inherit"/>
				</Backdrop>
			</div>
		)
	}
}

export default App

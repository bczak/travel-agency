import React from 'react'
import API from './api'
import 'react-date-range/dist/styles.css' // main style file
import 'react-date-range/dist/theme/default.css' // theme css file
import {DateRangePicker} from 'react-date-range'
import Icon from '@material-ui/core/Icon'

import {
	AppBar,
	Paper,
	Typography,
	Button,
	Toolbar,
	Card,
	CardActions,
	CardMedia,
	CardContent,
	Slider,
	TextField
} from '@material-ui/core'
import Autocomplete from '@material-ui/lab/Autocomplete'
import List from '@material-ui/core/List'
import ListItem from '@material-ui/core/ListItem'
import ListItemText from '@material-ui/core/ListItemText'
import ListItemIcon from '@material-ui/core/ListItemIcon'

function getDate(date) {

	const ye = new Intl.DateTimeFormat('en', {year: 'numeric'}).format(date)
	const mo = new Intl.DateTimeFormat('en', {month: '2-digit'}).format(date)
	const da = new Intl.DateTimeFormat('en', {day: '2-digit'}).format(date)
	return `${da}.${mo}.${ye}`
}

class App extends React.Component {
	state = {
		tags: [],
		isSearchClicked: false,
		trips: []
	}

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
		let tag = await API.getAllTags()
		let cnt = await API.getAllCountries()
		this.setState({...this.state, tags: tag, countries: cnt})
	}

	constructor() {
		super()
		this.state = {
			tags: [],
			selectedTags: [],
			countries: [],
			value: [1000, 2000],
			cards: [],
			sort: 0,
			expanded: false,
			selectionRange: {
				startDate: new Date(),
				endDate: new Date(),
				key: 'selection'
			}
		}
	}

	handleChange = (event, newValue) => {
		this.setState({...this.state, value: newValue})
	}
	handleSelect = (ranges) => {
		console.log(ranges)
		this.setState({...this.state, selectionRange: {...(ranges.selection), key: this.state.selectionRange.key}})
	}

	search = async () => {
		let res = await API.getTrips(this.state.selectedTags.map(e => e.title))
		let cards = []

		for (let i = 0; i < res.length; i++) {
			if (res[i].price < this.state.value[0] || res[i].price > this.state.value[1]) continue
			cards.push(<Card className={'card'} variant={'outlined'} key={res[i].id}>
				<CardMedia
					className={'image'}
					image={res[i].imageLink}
					title={res[i].name}
				/>
				<CardContent>
					<Typography variant={'h4'}>{res[i].name}</Typography>
					{/*<Typography variant={'body2'}>text</Typography>*/}
					<List component="nav" aria-label="contacts">
						<ListItem>
							<ListItemIcon>
								<Icon>attach_money</Icon>
							</ListItemIcon>
							<ListItemText primary={res[i].price}/>
						</ListItem>
						<ListItem>
							<ListItemIcon>
								<Icon>history</Icon>
							</ListItemIcon>
							<ListItemText
								primary={Math.floor(Math.abs(new Date(res[i].startDate) - new Date(res[i].endDate)) / 1000 / 60 / 60 / 24) + ' days'}/>
						</ListItem>
						<ListItem>
							<ListItemIcon>
								<Icon>calendar_today</Icon>
							</ListItemIcon>
							<ListItemText
								primary={getDate(new Date(res[i].startDate)) + ' - ' + getDate(new Date(res[i].endDate))}/>
						</ListItem>
						<ListItem>
							<ListItemIcon>
								<Icon>explore</Icon>
							</ListItemIcon>
							<ListItemText primary={res[i].countries.map(e => e.name).join(',')}/>
						</ListItem>
					</List>
				</CardContent>
				<CardActions disableSpacing className={'actions'}>
					<Button variant={'outlined'} className={'more-button'} href={res[i].link}
									color={'primary'}>More</Button>
				</CardActions>
			</Card>)
		}
		this.setState({...this.state, cards: cards})
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
						<Button color="inherit" variant={'outlined'}>Admin</Button>
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
											variant="standard"
											label="Select tags"
											placeholder="Tags"
										/>
									)}
								/>
								<span className={'spacer'}/>
								<Autocomplete
									multiple
									id="cntrs-standard"
									options={this.state.countries}
									getOptionLabel={(option) => option.title}
									renderInput={(params) => (
										<TextField
											{...params}
											variant="standard"
											label="Select countries"
											placeholder="Countries"
										/>
									)}
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
						>Search</Button>

					</div>
				</Paper>
				<Paper className={'result'} variant={'outlined'} elevation={2}>
					<Paper className={'panel'} variant={'outlined'} elevation={3}>
						<div className="button-group">
							<Button>Sort By:</Button>
							<Button disableElevation variant={this.state.sort === 0 ? 'contained' : 'outlined'}
											color={'primary'}>Title</Button>
							<Button disableElevation variant={this.state.sort === 1 ? 'contained' : 'outlined'}
											color={'primary'}>Duration</Button>
							<Button disableElevation variant={this.state.sort === 2 ? 'contained' : 'outlined'} color={'primary'}>Low
								Price</Button>
							<Button disableElevation variant={this.state.sort === 3 ? 'contained' : 'outlined'} color={'primary'}>High
								Price</Button>
							<Button disableElevation variant={this.state.sort === 4 ? 'contained' : 'outlined'}
											color={'primary'}>Newest</Button>
							<Button disableElevation variant={this.state.sort === 5 ? 'contained' : 'outlined'}
											color={'primary'}>Latest</Button>
						</div>
					</Paper>
					{this.state.cards}
				</Paper>
			</div>
		)
	}
}

export default App

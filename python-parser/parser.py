import xmltodict
import json
import sys

if len(sys.argv) != 2:
    print('Error. Choose a tour.')
    exit(0)
filename = sys.argv[1]
file = open('tours/' + filename, 'r').read()

tour = xmltodict.parse(file)

out = ""

tour_url = tour['tour']['url']

out += tour['tour']['overview']['p'][0] + ' '

out += tour['tour']['whatyouneedtoknow']['ul']['li']
num = len(tour['tour']['itineraries']['itinerary'])

for i in range(num):
    for k in tour['tour']['itineraries']['itinerary'][i]['description']['p']:
        if k is None:
            continue
        out += k

places = set()
places.add(tour['tour']['destinationfrom'])
places.add(tour['tour']['destinationto'])
for i in str(tour['tour']['visitedcountries']).split(', '):
    places.add(i)

days = tour['tour']['nbdays']

dates = {}

for date in tour['tour']['availabilities']['availability']:
    dates[date['departdate']] = date['localpayment']

file = open('json/' + filename + '.json', 'w')
output = json.dump({
    'title': str(tour['tour']['tourname']),
    'link': tour_url,
    'dates': dates,
    'days': days,
    'description': out,
    "image": tour['tour']['tourimages']['tourimage'][0]['imgfile'],
    "countries": tour['tour']['visitedcountries']
}, file)
print(output)
print(str(tour['tour']['tourname']))

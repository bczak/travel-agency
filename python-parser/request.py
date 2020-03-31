import xmltodict
import requests
import os


# get tours
tours_xml = requests.get('https://www.tucantravel.com/affiliates-xml').content

tours_xml = str(tours_xml.split()[3].decode('utf-8'))

tours = xmltodict.parse(tours_xml)

for tour in tours['tourcodes']['tourcode']:
    file = open('tours/' + tour['code'], 'w')
    file.write(requests.get(tour['link']).content.decode('utf-8'))
    print(tour['code'])


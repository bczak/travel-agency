import json

database = {
    "host": "35.223.206.24",
    "database": "main_user",
    "user": "main_user",
    "password": "travel_postgres"
}



file = open('rules.json', 'r')
tmp = file.read()
rules = json.loads(tmp)
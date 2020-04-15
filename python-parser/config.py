import json

database = {
    "host": "tash.wtf",
    "database": "main_user",
    "user": "main_user",
    "password": "travel_postgres"
}

file = open('rules.json', 'r')
tmp = file.read()
rules = json.loads(tmp)

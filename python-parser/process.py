import json
import sys
import requests
import config
import nltk

filename = sys.argv[1]
file = open('json/' + filename + '.json', 'r')
json_data = file.read()
parsed_json = (json.loads(json_data))
# print(parsed_json)

words = set()

text = parsed_json['description']

for rule in config.rules:
    tokens = nltk.word_tokenize(text)
    print(tokens)

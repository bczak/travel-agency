import json
import sys
import requests
import config
import nltk
import string
import re
from nltk.corpus import stopwords

nltk.download('stopwords')
nltk.download('wordnet')
nltk.download('punkt')

filename = sys.argv[1]
file = open('json/' + filename + '.json', 'r')
json_data = file.read()
parsed_json = (json.loads(json_data))
# print(parsed_json)

words = set()

text = parsed_json['description']
text = re.sub(r'\d+', '', text)

translate_table = dict((ord(char), None) for char in string.punctuation)
text = text.translate(translate_table)

text = text.lower()

tokens = nltk.word_tokenize(text)

lem = nltk.WordNetLemmatizer()
tokens = [lem.lemmatize(token) for token in tokens]

stop = stopwords.words('english')
tokens = [token for token in tokens if token not in stop]

syns = []

for word in tokens:
    for syn in nltk.corpus.wordnet.synsets(word):
        for lemma in syn.lemmas():
            w = lemma.name()
            if w not in tokens or w not in syns:
                syns.append(lemma.name())

tokens = tokens + syns

for rule in config.rules:
    index = 0
    for word in config.rules[rule]:
        index += tokens.count(word)
    print(rule + ' : ' + str(index))

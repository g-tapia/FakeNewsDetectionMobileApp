from flask import Flask, request
app = Flask(__name__)
import model
from news_cleaner import review_cleaning
import numpy
from tensorflow.keras.preprocessing.sequence import pad_sequences

import warnings
warnings.filterwarnings('ignore')

from configs import voc_size, sent_length

classification = "No classification yet"
fakenews_ai = model.fakenewsBiGRU("./model_save/")

def news_to_input(news):
    onehot= [[word_idx.get(word, 0) for word in news.split()]]
    embedded_doc=pad_sequences(onehot,padding='pre',maxlen=sent_length)

    return embedded_doc

@app.route('/data', methods=['POST'])
def parse_request():
    global classification
    classification = "No classification yet"
    news = request.args.get('news')
    if news is None:
        return "No news"
    
    news = review_cleaning(news)
    
    news_input = news_to_input(news)
    output = fakenews_ai.predict(news_input)[0] > 0.5
    
    if output is True:
        classification = "Real News"
    else:
        classification = "Fake News"
    
    return classification

@app.route('/classification', methods=['GET'])
def return_classification():
    global classification
    ret = classification 
    classification = "No classfication yet"
    return ret
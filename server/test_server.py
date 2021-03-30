from flask import Flask, request
app = Flask(__name__)

@app.route('/test1', methods=['POST'])

def parse_request():
    #Test that we can send a POST request and receive the response

    return "POST return"



@app.route('/test2', methods=['POST'])
def parse_request():
    #Test that we can send a POST request with content 'news'
    news = request.args.get('news')
    
    return news

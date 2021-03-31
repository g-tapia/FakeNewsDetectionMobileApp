from flask import Flask, request
app = Flask(__name__)

@app.route('/test1', methods=['POST'])

def test2_request():
    #Test that we can send a POST request and receive the response

    return "POST return"



@app.route('/test2', methods=['POST'])
def test1_request():
    #Test that we can send a POST request with content 'news'
    news = request.args.get('news')
    
    return news

app.run(host="0.0.0.0", port=5000, debug=True)
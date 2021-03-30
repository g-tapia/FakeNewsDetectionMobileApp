Step 1:
Go to the model_save folder and follow the instructions there.

Step 1.1:
If you don't have Python, install it using Miniconda: https://docs.conda.io/en/latest/miniconda.html.
After that, install tensorflow: pip install tensorflow

Step 2:
Run these commands in the server folder:
[WINDOWS]
set FLASK_APP=server.py
python -m flask run

Note: You need to wait a bit. There will be a bunch of warnings but they don't matter. 
The server is running if you see this line at the end: * Running on http://127.0.0.1:5000/ (Press CTRL+C to quit)
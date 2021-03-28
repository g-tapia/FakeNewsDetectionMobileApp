import nltk
nltk.download('stopwords')
from nltk.corpus import stopwords
import re
import string
from nltk.stem.porter import PorterStemmer

def review_cleaning(news):
    ps = PorterStemmer()
    stop = stopwords.words('english')


    news = str(news).lower()
    text = re.sub('\[.*?\]', '', news)
    news = re.sub('https?://\S+|www\.\S+', '', news)
    news = re.sub('<.*?>+', '', news)
    news = re.sub('[%s]' % re.escape(string.punctuation), '', news)
    news = re.sub('\n', '', news)
    news = re.sub('\w*\d\w*', '', news)

    news = ' '.join([word for word in news.split() if word not in (stop)])

    news = ' '.join([ps.stem(word) for word in news.split() if not word in (stop)])


    return news
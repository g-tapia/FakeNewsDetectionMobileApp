import pickle

#model configs
voc_size=10000 + 1
sent_length=5000
embedding_vector_features=40

#onehot list
with open("./model_save/onehot.pkl", "rb") as infile:
      word_idx = pickle.load(infile)


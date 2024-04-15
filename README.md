# Makrov chAIn
Generate text using [Markov chain](https://en.wikipedia.org/wiki/Markov_chain), create dataset from exported telegram chat (WIP)

`from-tg telegram.json dataset.json` Generate a dataset from telegram exported chat.

`from-txt text.txt dataset.json`  Generate a dataset from text file. All sentences should be split by \n

`generate dataset.json 25` Generate sentence from dataset. 2 words max (set in command)

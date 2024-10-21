# index

## Usage
```
# Add card
$ curl -i -XPOST localhost:8000/api/cards -F frontText=HELLO -F backText=WORLD -F frontImages=@dog.jpg -F frontImages=@monkey.jpg -F backImages=@pheasant.jpg
```
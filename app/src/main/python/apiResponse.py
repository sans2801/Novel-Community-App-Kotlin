import requests
# def main(book_name):
#     response=requests.get(f'https://www.googleapis.com/books/v1/volumes?q={book_name}&key=AIzaSyAZUqwfQnNnOiV0L9MzfOj5lLGk0xXFS-0')
#     return response.json()

def main(genre):
    response=requests.get(f'https://www.googleapis.com/books/v1/volumes?q=+subject:{genre}')
    return response.json()

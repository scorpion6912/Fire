import requests
r = requests.post('http://127.0.0.1:5000/capteurs', json=[
         {
        "id": 10,
        "lon": 0.215,
        "lat": 1.258,
        "avlue": 98,
        "timestamp": '2023-12-21 13:09:44.000000'
    },
    {
        "id": 12,
        "lon": 0.215,
        "lat": 1.258,
        "avlue": 98,
        "timestamp": '2023-12-21 13:09:44.000000'
    }
    ])
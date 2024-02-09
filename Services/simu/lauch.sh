#!/bin/bash
python3 -m virtualenv passerelle
source passerelle/bin/activate
python3 -m pip install -r requirements.txt

python3 passerelle.py

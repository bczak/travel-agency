#!/usr/bin/bash


FILES=tours/*
for f in $FILES
do
  python3 parser.py ../$f
done


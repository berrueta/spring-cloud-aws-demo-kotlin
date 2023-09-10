#!/bin/sh

awslocal sqs create-queue --queue-name demo-queue

echo "Initialized."
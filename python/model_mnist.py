#!/usr/bin/env python2
# -*- coding: utf-8 -*-
'''
mnist模型训练程序
val_loss: 0.02655
val_acc: 0.9914
'''

from keras import Model,Input
from keras.layers import Conv2D, MaxPooling2D, AveragePooling2D
from keras.layers import Dense, Dropout, Activation, Flatten,Input
from keras.models import Model,load_model
from keras.optimizers import SGD,Adam
from keras.utils import np_utils
from keras.preprocessing.image import ImageDataGenerator
from keras.callbacks import ModelCheckpoint
from keras.datasets import mnist

import sys
import time
import argparse
import pandas as pd
import numpy as np


def trainAndSave():
  (X_train, Y_train), (X_test, Y_test) = mnist.load_data()  # 28 * 28

  X_train = X_train.astype('float32').reshape(-1, 28, 28, 1)
  X_test = X_test.astype('float32').reshape(-1, 28, 28, 1)
  X_train /= 255
  X_test /= 255
  print('Train:{},Test:{}'.format(len(X_train), len(X_test)))

  nb_classes = 10

  Y_train = np_utils.to_categorical(Y_train, nb_classes)
  Y_test = np_utils.to_categorical(Y_test, nb_classes)
  print('data success')

  input_tensor = Input((28, 28, 1))
  #28*28
  output = Conv2D(filters=6, kernel_size=(5, 5), padding='valid', use_bias=False)(input_tensor)
  output = Activation('relu')(output)
  #24*24
  output = MaxPooling2D(pool_size=(2, 2))(output)
  #12*12
  output = Conv2D(filters=16, kernel_size=(5, 5), padding='valid', use_bias=False)(output)
  output = Activation('relu')(output)
  #8*8
  output = MaxPooling2D(pool_size=(2, 2))(output)
  #4*4
  #1*1
  output = Flatten()(output)
  output = Dense(120, activation='relu')(output)
  output = Dense(84, activation='relu')(output)
  output = Dense(nb_classes, activation='softmax')(output)
  model = Model(input=input_tensor, outputs=output)
  model.summary()
  sgd = SGD(lr=0.01, decay=1e-6, momentum=0.9, nesterov=True)
  model.compile(loss='categorical_crossentropy', optimizer=sgd, metrics=['accuracy'])
  model.fit(X_train, Y_train, batch_size=64, nb_epoch=15, validation_data=(X_test, Y_test))
  model.save('./model/model_mnist.hdf5')
  score = model.evaluate(X_test, Y_test, verbose=0)
  print(score)

if __name__=='__main__':
  parser = argparse.ArgumentParser(description='MNIST')
  parser.add_argument('--batch-size', type=int, default=64, metavar='N',
                      help='input batch size for training (default: 64)')
  parser.add_argument('--test-batch-size', type=int, default=1000, metavar='N',
                      help='input batch size for testing (default: 1000)')
  parser.add_argument('--epochs', type=int, default=10, metavar='N',
                      help='number of epochs to train (default: 10)')
  parser.add_argument('--lr', type=float, default=0.01, metavar='LR',
                      help='learning rate (default: 0.01)')
  parser.add_argument('--momentum', type=float, default=0.5, metavar='M',
                      help='SGD momentum (default: 0.5)')
  parser.add_argument('--no-cuda', action='store_true', default=False,
                      help='disables CUDA training')
  parser.add_argument('--seed', type=int, default=1, metavar='S',
                      help='random seed (default: 1)')
  parser.add_argument('--log-interval', type=int, default=10, metavar='N',
                      help='how many batches to wait before logging training status')
  parser.add_argument('--save-model', action='store_true', default=False,
                      help='for Saving the current Model')
  parser.add_argument('--deepgini', action='store_true', default=False,
                      help='use DeepGini to prioritizing testcases')
  parser.add_argument('--dg-batch-size', type=int, default=10, metavar='N',
                      help='number of testcases for each deepgini process')
  args = parser.parse_args()

  trainAndSave()

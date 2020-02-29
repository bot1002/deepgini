#!/usr/bin/env python2
# -*- coding: utf-8 -*-

import pandas as pd
import numpy as np

import glob
import os
import argparse


def apfd(right,sort):
  length = np.sum(sort != 0)
  if length != len(sort):
    sort[sort == 0] = np.random.permutation(len(sort) - length) + length + 1
  sum_all = np.sum(sort.values[[right.values != 1]])
  n = len(sort)
  m = pd.value_counts(right)[0]
  return 1 - float(sum_all) / (n * m) + 1. / (2 * n)


def doApfd(name):
  lst = glob.glob('./python/output_' + name + '/*')
  data_dict = {}
  for i in lst:
    name = os.path.basename(i)[:-4]
    data_dict[name] = pd.read_csv(i,index_col = 0)

  for key in data_dict.keys():
    # print(key)
    # print('覆盖率:{}'.format(data_dict[key].rate.iloc[0]))
    # print('错误样本数:{}'.format(pd.value_counts(data_dict[key].right)[0]))
    # print('总样本数:{}'.format(len(data_dict[key])))
    # print('覆盖最少样本数:{}'.format((data_dict[key].cam!=0).sum()))
    # # if 'ctm' in data_dict[key].columns:
    # #   print('CTM:{}'.format(apfd(data_dict[key].right,data_dict[key].ctm)))

    # print('CAM:{}'.format(apfd(data_dict[key].right,data_dict[key].cam)))
    # print('==============================')
    return apfd(data_dict[key].right, data_dict[key].cam)

if __name__ == '__main__':
  parser = argparse.ArgumentParser(description='APFD')
  parser.add_argument('--mnist', action='store_true', default=False,
                      help='apfd on mnist')
  parser.add_argument('--cifar10', action='store_true', default=False,
                      help='apfd on cifar')
  parser.add_argument('--svnh', action='store_true', default=False,
                      help='apfd on svnh')                      
  args = parser.parse_args()

  if args.mnist == True:
    print(doApfd('mnist'))  # awkward IPC
  if args.cifar10 == True:
    print(doApfd('cifar10'))
  if args.svnh == True:
    print(doApfd('svnh'))

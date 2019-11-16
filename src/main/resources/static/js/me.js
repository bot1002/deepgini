"use strict"

function displayUploadFilename(file) {
  $("#uploadlabel").html(file.name);
}

function displayUploadFilename1(file) {
  $("#uploadlabel1").html(file.name);
}

function leNet(){
  document.getElementById("network").innerText = "LeNet-5";
}
function VGG() {
  document.getElementById("network").innerText = "VGG-16";
}
function ResNet() {
  document.getElementById("network").innerText = "ResNet-52";
}
function MNIST() {
  document.getElementById("testSet").innerText = "MNIST";
}
function CIFAR() {
  document.getElementById("testSet").innerText = "CIFAR-10";
}
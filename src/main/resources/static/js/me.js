"use strict"

function displayUploadFilename(file) {
  $("#uploadlabel").html(file.name);
  let formdata = new FormData();
  formdata.append('file', file);
  formdata.append('type', 'model');
  let url = 'http://localhost:8080/uploadFile'
  $.ajax({
    url: url,
    type: "POST",
    data:formdata,
    dataType: "json",
    processData: false,  // 告诉jQuery不要去处理发送的数据
    contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
    success: function (res) {
    }
  })
  alert('上传成功！');
}

function displayUploadFilename1(file) {
  let formdata = new FormData();
  formdata.append('file', file);
  formdata.append('type', 'test');
  let url = 'http://localhost:8080/uploadFile'
  $.ajax({
    url: url,
    type: "POST",
    data:formdata,
    dataType: "json",
    processData: false,  // 告诉jQuery不要去处理发送的数据
    contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
    success: function (res) {
    }
  })
  alert('上传成功！');
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
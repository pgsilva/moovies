var express = require('express');
var consign = require('consign');
var bodyParser = require('body-parser');
var app = express();

//configuracoes express js
app.use(express.static('./public')); //liberando uma pasta para ser acessivel ao navegador
app.use(bodyParser.json());

consign({ cwd: 'app' })
        .include('model')
        .then('api')
        .then('routes')
        .into(app);//libera a pasta route pra ser carregado pelo express

module.exports = app;

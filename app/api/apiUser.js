const api = {};
const User = require('../model/user');
const mongoose = require('mongoose');
const user = mongoose.model('User');

api.carregaUsers = function async(req, res) {
    user
        .find({})
        .then(function (users) {
            res.json(users);
        }, function (err) {
            console.log('Ocorreu um ERRO na busca ');
            res.status(500).json(err);
        });
};

api.carregaUsersFiltro = function async(req, res) {
    var filtro = req.body;
    user
        .find(filtro)
        .then(function (users) {
            if (!users) throw Error('nenhum registro encontrato');
            res.json(users);
        }, function (err) {
            console.log('Ocorreu um ERRO na busca ');
            res.status(500).json(err);
        });
};

api.registraUser = function async(req, res) {

    var model = req.body;
    user
        .create(model)
        .then(function (result) {
            res.json(result);
        }, function (err) {
            console.log('Ocorreu um ERRO no registro ');
            res.status(500).json(err);
        });
};

api.removerUser = function async(req, res) {
    user
        .remove({ _id: req.body.id })
        .then(function () {
            res.sendStatus(204); // ok porem nada vai ser enviado
        }, function (err) {
            console.log(err);
            res.status(500).json(err);
        });
};

api.atualizaUser = function async(req, res) {
    var update = req.body.update;
    user
        .findOneAndUpdate(req.body.id, update)
        .then(function(result){
            res.json(result);
        }, function(err){
            console.log(err);
            res.status(500).json(err);
        });
        
};

module.exports = api;
const User = require('../model/user');
const mongoose = require('mongoose');
var api={};

api.carregaUsers = function async(req, res){
    try{
        var user = mongoose.model('User');
        user.find(function(err, users){
            if(err) res.status(500).json(err);
            
            res.json(users);
        });
    }catch(err){
        return res.status(400).send({error:'Ocorreu um erro na busca '});
    };
};

api.registraUser = function async(req, res){
    try{
        const user = User.create(req.body);
    }catch(err){
        return res.status(400).send({error:'Ocorreu um erro no cadastro '});
    };
};

api.buscaFiltro = function async(req, res){
    try{
        const user = User.create(req.body);
    }catch(err){
        return res.status(400).send({error:'Ocorreu um erro no na busca por filtro '});
    };
};

api.removerUser = function async(req, res){
    try{
        const user = User.create(req.body);
    }catch(err){
        return res.status(400).send({error:'Ocorreu um erro na exclusão '});
    };
};

api.atualizaUser = function async(req, res){
    try{
        const user = User.create(req.body);
    }catch(err){
        return res.status(400).send({error:'Ocorreu um erro na atualizção '});
    };
};

module.exports = api;
module.exports = function (app) {
    var apiUser = app.api.apiUser;

    //config de rotas
    app.get('/api/carregaUsers', apiUser.carregaUsers);
    
    
};


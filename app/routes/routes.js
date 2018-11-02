module.exports = function (app) {
    var apiUser = app.api.apiUser;

    //config de rotas USUARIO
    app.get('/api/carregaUsers', apiUser.carregaUsers);
    app.post('/api/carregaUsers', apiUser.carregaUsersFiltro);
    app.post('/api/removeUserId', apiUser.removerUser);
    app.post('/api/registrarUser', apiUser.registraUser);
    app.post('/api/atualizarUser', apiUser.atualizaUser)


};


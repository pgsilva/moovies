module.exports = function (uri) {
    const mong = require('mongoose');

    mong.connect('mongodb://' + uri, { useNewUrlParser: true });
    mong.Promise = global.Promise;

    mong.connection.on('connected', function () {
        console.log('conectado ao MongoDB!');
    });

    mong.connection.on('error', function (error) {
        console.log('erro de conexao: ' + error);
    });

    mong.connection.on('disconnected', function () {
        console.log('desconectado do MongoDB');
    });

    process.on('SIGINT', function () {
        mong.connection.close(function () {
            console.log('desconectado pelo término da aplicação');
            process.exit(0);
        });
    });
}
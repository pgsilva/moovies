const mong = require('mongoose');

const UserSchema = new mong.Schema({
    nome:{
        type:String,
        require: true,
    },
    email:{
        type:String,
        require: true,
        lowercase: true,
    },
    pass:{
        type:String,
        require: true,
        select:false,
    },
    criacao:{
        type:Date,
        default: Date.now,
    }
});

const User = mong.model('User', UserSchema);
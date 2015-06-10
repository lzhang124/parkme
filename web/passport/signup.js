var LocalStrategy = require('passport-local').Strategy;
// var User = require('../models/userModel');
var bCrypt = require('bcrypt-nodejs');

module.exports = function(passport){

    passport.use('signup', new LocalStrategy({
            passReqToCallback : true // allows us to pass back the entire request to the callback
        },
        function(req, username, password, done) {

            findOrCreateUser = function(){
                User.findOne({ 'username' :  username }, function(err, user) {
                    if (err) {
                        console.log('Error in signup: '+err);
                        return done(err);
                    };
                    if (user) {
                        return done(null, false, req.flash( 'message', 'User already exists with username: '+username ));
                    } else {
                        var newUser = new User();

                        // set the user's local credentials
                        newUser.firstname = req.param('firstname');
                        newUser.lastname = req.param('lastname');
                        newUser.username = username;
                        newUser.password = createHash(password);
                        newUser.phone = req.param('phone');
                        newUser.hasReq = "";

                        newUser.save(function(err) {
                            if (err) {
                                console.log('Error in saving user: '+err);  
                                throw err;
                            };
                            return done(null, newUser, req.flash( 'message', 'Welcome, '+newUser.firstname ));
                        });
                    };
                });
            };
            // Delay the execution of findOrCreateUser and execute the method
            // in the next tick of the event loop
            process.nextTick(findOrCreateUser);
        })
    );

    var createHash = function(password){
        return bCrypt.hashSync(password, bCrypt.genSaltSync(10), null);
    };
};

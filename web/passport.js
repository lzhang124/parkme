var LocalStrategy = require('passport-local').Strategy;
var bCrypt = require('bcrypt-nodejs');
var https = require('https');
var querystring = require('querystring');

module.exports = function(passport) {

    // API CALLS
    var findById = function(id, fn) {
        https.get('https://127.0.0.1:8443/accountById?id=' + id, function(res) {
            var data = '';

            res.on('data', function(chunk) {
                data += chunk;
            });

            res.on('end', function() {
                if (data === '') {
                    return fn(null, null);
                }
                var user = JSON.parse(data);
                return fn(null, user);
            });
        }).on('error', function(e) {
            console.log('Error: ', e);
            return fn(e, null);
        });
    };

    var findByUsername = function(username, fn) {
        https.get('https://127.0.0.1:8443/accountByUsername?username=' + username, function(res) {
            var data = '';

            res.on('data', function(chunk) {
                data += chunk;
            });

            res.on('end', function() {
                if (data === '') {
                    return fn(null, null);
                }
                var user = JSON.parse(data);
                return fn(null, user);
            });
        }).on('error', function(e) {
            console.log('Error: ', e);
            return fn(e, null);
        });
    };

    var createNewUser = function(firstName, lastName, username, password, fn) {
        var data = querystring.stringify({
            firstName: firstName,
            lastName: lastName,
            username: username,
            password: password
        });

        var options = {
            host: '127.0.0.1',
            port: 8443,
            path: '/newAccount',
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        };

        var req = https.request(options, function(res) {
            res.setEncoding('utf8');
            var data = '';

            res.on('data', function(chunk) {
                data += chunk
            });

            res.on('end', function() {
                var user = JSON.parse(data);
                return fn(null, user);
            });
        });
        req.on('error', function(e) {
            console.log('Error: ', e);
            return fn(e, null);
        });

        req.write(data);
        req.end();
    };

    var isValidPassword = function(user, password) {
        return bCrypt.compareSync(password, user.password);
    };

    var createHash = function(password) {
        return bCrypt.hashSync(password, bCrypt.genSaltSync(10));
    };

    // Passport needs to be able to serialize and deserialize users to support persistent login sessions
    passport.serializeUser(function(user, done) {
        console.log('serializing user:', user);
        done(null, user.id);
    });

    passport.deserializeUser(function(id, done) {
        findById(id, function(err, user) {
            console.log('deserializing user:', user);
            done(err, user);
        });
    });

    // LOGIN
    passport.use('login', new LocalStrategy({
        passReqToCallback : true
    }, function(req, username, password, done) {
        findByUsername(username, function(err, user) {
            if (err) {
                console.log('Error in login: ' + err);
                return done(err);
            };
            if (!user) {
                console.log('User not found with username: ' + username);
                return done(null, false, req.flash( 'message', 'User not found with username: ' + username ));
            };
            if (!isValidPassword(user, password)){
                console.log('Invalid password');
                return done(null, false, req.flash( 'message', 'Invalid password' ));
            };
            console.log('Welcome, ' + user.firstName);
            return done(null, user, req.flash( 'message', 'Welcome, ' + user.firstName ));
        });
    }));

    // SIGNUP
    passport.use('signup', new LocalStrategy({
        passReqToCallback : true
    }, function(req, username, password, done) {
        process.nextTick(function() {
            findByUsername(username, function(err, user) {
                if (err) {
                    console.log('Error in signup: ' + err);
                    return done(err);
                };
                if (user) {
                    console.log('User already exists with username: ' + username);
                    return done(null, false, req.flash( 'message', 'User already exists with username: ' + username ));
                } else {
                    var firstName = req.param('firstName');
                    var lastName = req.param('lastName');
                    var password = createHash(password);
                    
                    createNewUser(firstName, lastName, username, password, function(err, user) {
                        if (err) {
                            console.log('Error in saving user: ' + err);  
                            throw err;
                        };
                        console.log('Welcome, ' + user.firstName);
                        return done(null, user, req.flash( 'message', 'Welcome, ' + user.firstName ));
                    });
                };
            });
        });
    }));
};

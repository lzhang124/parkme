var LocalStrategy = require('passport-local').Strategy;
var bcrypt = require('bcrypt-nodejs');
var http = require('http');
var querystring = require('querystring');

var url = 'http://52.25.5.25:8080/';

module.exports = function(passport) {

  // API CALLS
  var findById = function(id, fn) {
    http.get(url + 'accountById?accountId=' + id, function(res) {
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

  var findByEmail = function(email, fn) {
    http.get(url + 'accountByEmail?email=' + email, function(res) {
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

  var createNewUser = function(firstName, lastName, email, password, fn) {
    var data = querystring.stringify({
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password
    });

    var options = {
      host: '52.25.5.25',
      port: 8080,
      path: '/newAccount',
      method: 'POST',
      headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
      }
    };

    var req = http.request(options, function(res) {
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
    return bcrypt.compareSync(password, user.password);
  };

  var createHash = function(password) {
    return bcrypt.hashSync(password, bcrypt.genSaltSync(8), null);
  };

  passport.serializeUser(function(user, done) {
    console.log('serializing user:', user);
    done(null, user.id);
  });

  passport.deserializeUser(function(id, done) {
    findById(id, function(err, user) {
      console.log('deserializing user:', user.id);
      done(err, user);
    });
  });

  // LOGIN
  passport.use('login', new LocalStrategy({
    usernameField: 'email',
    passwordField: 'password',
    passReqToCallback : true
  }, function(req, email, password, done) {
    findByEmail(email, function(err, user) {
      if (err) {
        console.log('Error in login: ' + err);
        return done(err);
      };
      if (!user) {
        console.log('User not found with email: ' + email);
        return done(null, false, req.flash( 'message', 'User not found with email: ' + email ));
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
    usernameField: 'email',
    passwordField: 'password',
    passReqToCallback : true
  }, function(req, email, password, done) {
    process.nextTick(function() {
      findByEmail(email, function(err, user) {
        if (err) {
          console.log('Error in signup: ' + err);
          return done(err);
        };
        if (user) {
          console.log('User already exists with email: ' + email);
          return done(null, false, req.flash( 'message', 'User already exists with email: ' + email ));
        } else {
          var first = req.param('firstName');
          var last = req.param('lastName');
          var pw = createHash(password);
          
          createNewUser(first, last, email, pw, function(err, user) {
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

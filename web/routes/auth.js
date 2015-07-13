var express = require('express');
var router = express.Router();
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var http = require('http');
var querystring = require('querystring');

var url = 'http://127.0.0.1:8080';

passport.serializeUser(function(user, done) {
    done(null, user);
});

passport.deserializeUser(function(obj, done) {
    done(null, obj);
});

// SIGNUP
var signup = function(firstName, lastName, email, password, fn) {
  var data = querystring.stringify({
    firstName: firstName,
    lastName: lastName,
    email: email,
    password: password
  });

  var options = {
    host: '127.0.0.1',
    port: 8080,
    path: '/signup',
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
      if (data === '') {
        return fn(null, null);
      }
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

passport.use('signup', new LocalStrategy({
  usernameField: 'email',
  passwordField: 'password',
  passReqToCallback : true
}, function(req, email, password, done) {
  process.nextTick(function() {
    var first = req.param('firstName');
    var last = req.param('lastName');
    signup(first, last, email, password, function(err, user) {
      if (err) {
        console.log('Error in signup: ' + err);
        return done(err);
      };
      if (user === null) {
        console.log('User already exists with email: ' + email);
        return done(null, false, req.flash( 'message', 'User already exists with email: ' + email ));
      }
      console.log('Welcome, ' + user.firstName);
      return done(null, user, req.flash( 'message', 'Welcome, ' + user.firstName ));
    });
  });
}));

// LOGIN
var login = function(email, password, fn) {
  http.get(url + '/login?email=' + email + 'password=' + password, function(res) {
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

passport.use('login', new LocalStrategy({
  usernameField: 'email',
  passwordField: 'password',
  passReqToCallback : true
}, function(req, email, password, done) {
  login(email, password, function(err, user) {
    if (err) {
      console.log('Error in login: ' + err);
      return done(err);
    };
    if (user === null) {
      console.log('User not found with email: ' + email);
      return done(null, false, req.flash( 'message', 'User not found with email: ' + email ));
    };
    if (user === false){
      console.log('Invalid password');
      return done(null, false, req.flash( 'message', 'Invalid password' ));
    };
    console.log('Welcome, ' + user.firstName);
    return done(null, user, req.flash( 'message', 'Welcome, ' + user.firstName ));
  });
}));


/* Sign up */
router.post('/signup', passport.authenticate('signup', {
  successRedirect: '/q9xwGoXLGQ/register',
  failureRedirect: '/q9xwGoXLGQ/signup',
  failureFlash: true,
  successFlash: true
}));

/* Login */ 
router.post('/login', passport.authenticate('login', {
  successRedirect: '/q9xwGoXLGQ',
  failureRedirect: '/q9xwGoXLGQ/login',
  failureFlash: true,
  successFlash: true
}));

/* Logout */ 
router.get('/logout', function(req, res) {
  req.logout();
  req.session.destroy();
  res.redirect('/q9xwGoXLGQ');
});

module.exports = router;

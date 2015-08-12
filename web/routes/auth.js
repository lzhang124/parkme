var express = require('express');
var router = express.Router();
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var http = require('http');
var querystring = require('querystring');

var url = 'http://52.25.5.25:8080';

passport.serializeUser(function(user, done) {
  console.log('serializing:' +  user.firstName);
  done(null, user);
});

passport.deserializeUser(function(user, done) {
  console.log('deserializing:' +  user.firstName);
  done(null, user);
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
    host: '52.25.5.25',
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
      data += chunk;
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
      }
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
  var data = querystring.stringify({
    email: email,
    password: password
  });

  var options = {
    host: '52.25.5.25',
    port: 8080,
    path: '/login',
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  };

  var req = http.request(options, function(res) {
    res.setEncoding('utf8');
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
  });
  req.on('error', function(e) {
    console.log('Error: ', e);
    return fn(e, null);
  });

  req.write(data);
  req.end();
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
  successRedirect: '/miidUx5rtG/register',
  failureRedirect: '/miidUx5rtG',
  failureFlash: true,
  successFlash: true
}));

/* Login */ 
router.post('/login', passport.authenticate('login', {
  successRedirect: '/miidUx5rtG',
  failureRedirect: '/miidUx5rtG',
  failureFlash: true,
  successFlash: true
}));

/* Logout */ 
router.get('/logout', function(req, res) {
  req.session.destroy();
  req.logout();
  res.redirect('/miidUx5rtG');
});

module.exports = router;

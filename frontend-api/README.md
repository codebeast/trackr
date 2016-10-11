--Compile to node
NODE_ENV=production browserify -t [ reactify --es6 ] assets/javascripts/main.js | uglifyjs > public/javascripts/compiled.min.js

--Watch and compile
watchify -v -d -t [ reactify --es6 ] assets/javascripts/main.js -o public/javascripts/compiled.js

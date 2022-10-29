var gulp = require('gulp');
var webpack = require('webpack-stream');
var merge = require('merge2');
var rev = require('gulp-rev');
var revReplace = require("gulp-rev-replace");
var clean = require('gulp-clean');

// remove cache on static folder
gulp.task('drop-cache', function () {
    return gulp.src(['./src/main/resources/app/static'], {read: false}).pipe(clean({force: true}));
});

// generate dist via webpack
gulp.task('webpack', ['drop-cache'], () => {
    const streams = [];
    streams.push(gulp.src('./src/main/resources/app/**/*.ts')
            .pipe(webpack(require('./webpack.config.js')))
            .on('error', function handleError() {
                this.emit('end'); // Recover from errors
            })
            .pipe(gulp.dest('./src/main/resources/app/static/dist')))
    return merge(streams);
});

// generate rev manifest
gulp.task('rev', ['webpack'], () => {
    return gulp.src('./src/main/resources/app/static/dist/**/*.js')
        .pipe(rev())
        .pipe(gulp.dest('./src/main/resources/app/static/dist'))
        .pipe(rev.manifest())
        .pipe(gulp.dest('./src/main/resources/app/static'));
});

// build html within chunk js
gulp.task('build', ['rev'], () => {
    const refs = gulp.src("./src/main/resources/app/index.html")
        .pipe(revReplace({manifest: gulp.src("./src/main/resources/app/static/rev-manifest.json")}))
        .pipe(gulp.dest("./src/main/resources/app/static"));

    return merge[refs];
});
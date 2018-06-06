var gulp = require('gulp');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');
var cssMin = require('gulp-css');
var gutil = require('gulp-util');


gulp.task('css', function () {

    gulp.src([
        './magnific-popup/magnific-popup.css',
        './css/creative.css',

        './css/animate.css',
        './css/bootstrap.css',
        './css/fonts.css',
        './css/md-icons.css',
        './css/angular-toggle-switch-bootstrap-3.css',
        './css/highlight.css',
        './bootstrap-select/css/nya-bs-select.css',
        './css/font-awesome-animation.css',
        './kdate/css/jquery.calendars.picker.css',
        './css/select.css',
        './chosen/chosen.css',
        './css/marquee.css'
    ])
        .pipe(concat('app.css'))
        .pipe(cssMin())
        .pipe(gulp.dest('./'));

});

gulp.task('scripts', function () {

    gulp.src([

        './js/material.js',
        './js/fontawesome.js',
        './js/jquery.js',

        './kdate/js/jquery.calendars.js',
        './kdate/js/jquery.calendars-ar.js',
        './kdate/js/jquery.calendars-ar-EG.js',
        './kdate/js/jquery.plugin.js',
        './kdate/js/jquery.calendars.picker.js',
        './kdate/js/jquery.calendars.picker-ar.js',
        './kdate/js/jquery.calendars.picker-ar-EG.js',
        './kdate/js/jquery.calendars.picker.lang.js',
        './kdate/js/jquery.calendars.ummalqura.js',
        './kdate/js/jquery.calendars.ummalqura-ar.js',
        './kdate/js/jquery.calendars.plus.js',

        './js/jquery-ui.js',
        './js/angular.js',
        './js/angular-sanitize.js',
        './js/angular-ui-router.js',
        './js/angular-animate.js',
        './js/angular-touch.js',
        './js/angular-filter.js',

        './angular-spinner/spin.js',
        './angular-spinner/angular-spinner.js',
        './angular-spinner/angular-loading-spinner.js',

        './js/ui-bootstrap.js',
        './js/ui-bootstrap-tpls.js',

        './js/select.js',
        './js/marquee.js',

        './sockjs/sockjs.js',
        './stomp-websocket/lib/stomp.js',
        './ng-stomp/ng-stomp.js',
        './kdate/kdate.module.js',
        './kdate/kdate.filter.js',
        './kdate/kdate.picker.js',
        './js/underscore.js',
        './js/lrDragNDrop.js',
        './js/contextMenu.js',
        './js/lrStickyHeader.js',
        './js/smart-table.js',
        './js/stStickyHeader.js',
        './js/angular-pageslide-directive.js',
        './js/elastic.js',
        './js/scrollglue.js',
        './ng-upload/angular-file-upload.js',
        './bootstrap-select/js/nya-bs-select.js',
        './js/angular-css.js',
        './js/angular-timer-all.js',
        './full-screen/angular-fullscreen.js',
        './animate-counter/angular-counter.js',
        './js/angular-focusmanager.js',
        './js/jcs-auto-validate.js',
        './js/angular-toggle-switch.js',
        './js/chosen.jquery.js',
        './chosen/angular-chosen.js',
        './js/sortable.js',
        './js/FileSaver.js',
        './js/jquery.noty.packaged.js',
        './scrollreveal/scrollreveal.js',
        './magnific-popup/jquery.magnific-popup.js',
        './js/creative.js',
        './angular-chart/Chart.js',
        './angular-chart/angular-chart.js',
        './js/datetime.js',

        './init/config/config.js',
        './init/factory/errorHandleFactory.js',
        './init/factory/companyFactory.js',
        './init/factory/customerFactory.js',
        './init/factory/sellerFactory.js',
        './init/factory/productFactory.js',
        './init/factory/productPurchaseFactory.js',
        './init/factory/contractFactory.js',
        './init/factory/contractAttachFactory.js',
        './init/factory/contractProductFactory.js',
        './init/factory/contractPaymentFactory.js',
        './init/factory/contractPremiumFactory.js',
        './init/factory/bankFactory.js',
        './init/factory/bankTransactionFactory.js',
        './init/factory/attachTypeFactory.js',
        './init/factory/personFactory.js',
        './init/factory/smsFactory.js',
        './init/factory/teamFactory.js',

        './init/service/service.js',
        './init/directive/directive.js',
        './init/filter/filter.js',
        './init/run/run.js',

        './partials/home/home.js',
        './partials/menu/menu.js',
        './partials/customer/customerCreateUpdate.js',
        './partials/customer/customerFilter.js',
        './partials/customer/customerSendMessage.js',
        './partials/seller/sellerCreateUpdate.js',
        './partials/seller/sellerFilter.js',
        './partials/productPurchase/productPurchaseFilter.js',
        './partials/contract/contractFilter.js',
        './partials/contract/contractCreate.js',
        './partials/contract/contractUpdate.js',
        './partials/contract/contractOldCreate.js',
        './partials/contract/contractDetails.js',
        './partials/contractProduct/contractProductCreate.js',
        './partials/contractPayment/contractPaymentFilter.js',
        './partials/contractPayment/contractPaymentCreate.js',
        './partials/contractPremium/contractPremiumFilter.js',
        './partials/contractPremium/contractPremiumCreate.js',
        './partials/contractPremium/contractPremiumSendMessage.js',
        './partials/bankTransaction/bankTransactionFilter.js',
        './partials/bankTransaction/depositCreate.js',
        './partials/bankTransaction/withdrawCreate.js',
        './partials/bankTransaction/transferCreate.js',
        './partials/bankTransaction/withdrawCashCreate.js',
        './partials/product/parentCreateUpdate.js',
        './partials/product/productCreateUpdate.js',
        './partials/product/productFilter.js',
        './partials/product/productPurchaseCreate.js',
        './partials/person/personCreateUpdate.js',
        './partials/team/teamCreateUpdate.js',
        './partials/modal/confirmModal.js'

    ])
        .pipe(concat('app.js'))
        .pipe(uglify())
        .on('error', function (err) {
            gutil.log(gutil.colors.red('[Error]'), err.toString());
        })
        .pipe(gulp.dest('./'))

});

gulp.task('default', ['css', 'scripts']);
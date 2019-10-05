const fs = require('fs-extra');
const concat = require('concat');

(async function build() {
  const files = [
    './target/dist/portlet/runtime-es5.js',
    './target/dist/portlet/polyfills-es5.js',
    './target/dist/portlet/scripts.js',
  ];

  await concat(files, './src/main/webapp/js/runtime.js');
  await fs.copyFile('./target/dist/portlet/styles.css', './src/main/webapp/css/books-catalog.css');
  await fs.copyFile('./target/dist/portlet/main-es5.js', './src/main/webapp/js/books-catalog.js');
})();

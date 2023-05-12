## 特性

- **最新技术栈**：使用 Vue3/Vite3 等前端前沿技术开发
- **主题**：可配置的主题
- **国际化**：内置完善的国际化方案
- **常用组件**：内置完善的常用组件封装
- **Tauri**：Tauri 编译桌面应用
- **PWA**：内置 PWA

## 准备

- [Node](http://nodejs.org/) 和 [Git](https://git-scm.com/) -项目开发环境
- [Vite](https://cn.vitejs.dev/) - 熟悉 Vite 特性
- [Vue3](https://v3.cn.vuejs.org/) - 熟悉 Vue 基础语法
- [Es6+](http://es6.ruanyifeng.com/) - 熟悉 Es6 基本语法
- [Vue-Router-Next](https://next.router.vuejs.org/zh/) - 熟悉 Vue-Router 基本使用
- [Element-Plus](https://element-plus.gitee.io/#/zh-CN/) - Ui 基本使用

## 安装使用

### 1. 准备

首先您必需要安装 Rust 及其他系统依赖。 [安装 Rust](https://tauri.app/zh/v1/guides/getting-started/prerequisites)

### 2. 获取项目代码（Https or SSH）

### 3. 安装依赖

推荐使用`pnpm`

```bash
pnpm i
```

`npm`安装

```bash
npm install

# 建议不要直接使用 cnpm 安装以来，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
# 如果下载依赖慢可以使用淘宝镜像源安装依赖
npm install --registry=https://registry.npm.taobao.org

```

### 4. 运行

```bash
npm run tauri dev
```

### 5. 打包应用

```bash
npm run tauri build
```

## 浏览器支持

本地开发推荐使用`Chrome 80+` 浏览器

支持现代浏览器, 不支持 IE

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt=" Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>IE | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt=" Edge" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](http://godban.github.io/browsers-support-badges/)</br>Safari |
| :-: | :-: | :-: | :-: | :-: |
| not support | last 2 versions | last 2 versions | last 2 versions | last 2 versions |

## 项目中使用的相关仓库

### vite 相关

- [vite-plugin-compression](https://github.com/anncwb/vite-plugin-compression/) - 资源压缩支持 Gzip or brotli
- [vite-plugin-style-import](https://github.com/anncwb/vite-plugin-style-import/) - 动态引入组件库样式
- [vite-plugin-svg-icons](https://github.com/anncwb/vite-plugin-svg-icons/) - SVG 雪碧图
- [vite-plugin-theme-preprocessor](https://github.com/GitOfZGT/vite-plugin-theme-preprocessor/) - 动态改变主题样式
- [vite-plugin-pwa](https://vite-plugin-pwa.netlify.app/) - PWA

### 规范相关

- [EsLint](https://eslint.org/) - js 语法检测
- [StyleLint](https://stylelint.io/) - 样式语法检测
- [CommitLint](https://commitlint.js.org/#/) - git commit 提交规范检测

## 项目目录结构

```base
.
├── build                         #全局公共配置目录
├── public                        #公共静态文件目录
├── src                           #项目代码目录
│   ├── App.vue                   #主vue模块
│   ├── assets                    #项目静态文件目录
│   ├── components                #公共组件
│   ├── layouts                   #布局目录
│   ├── locales                   #国际化配置
│   ├── main.js                   #入口文件
│   ├── router                    #路由
│   ├── store                     #vuex
│   ├── styles                    #公共样式
│   ├── utils                     #公共方法
│   └── views                     #存放vue页面目录
├── src-tauri                     #tauri配置
├── LICENSE
├── README.md
├── .editorconfig                 #规范编译器编码样式文件
├── .env.development              #开发环境变量
├── .env.production               #生产环境变量
├── .env.staging                  #测试环境变量
├── .eslintrc.js                  #esLint配置文件
├── .eslintrcignore               #esLint忽略配置文件
├── stylelint.config.js           #styleLint配置文件
├── .stylelintignore              #styleLint忽略配置文件
├── commitlint.config.js          #commitLint配置文件
├── prettier.config.js            #prettier配置文件
├── .prettierignore               #prettier忽略配置文件
├── index.html                    #根模板
├── jsconfig.json
├── package-lock.json
├── package.json
└── vite.config.js                #vite配置文件
```

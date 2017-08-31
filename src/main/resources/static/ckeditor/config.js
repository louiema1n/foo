/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
    config.language = 'zh-cn';
    config.height = 500;
    config.toolbarCanCollapse = true;


    // 工具栏配置（'/'表示换行）
    config.toolbarGroups = [
        { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
        { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
        { name: 'insert', groups: [ 'insert' ] },
        '/',
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
        { name: 'links', groups: [ 'links' ] },
        { name: 'styles', groups: [ 'styles' ] },
        { name: 'colors', groups: [ 'colors' ] },
        { name: 'tools', groups: [ 'tools' ] },
        { name: 'others', groups: [ 'others' ] },
    ];
    // 工具栏移除按钮
    config.removeButtons = 'BidiLtr,BidiRtl,Language,Flash,Iframe,Save';
    //注册插件
    config.extraPlugins = 'html5video,html5audio';
    // 文件上传路径
    config.filebrowserUploadUrl="/file/ckupload";
    config.filebrowserBrowseUrl = '/file';
    // 图片预览地址
    config.filebrowserImageBrowseUrl = '/file';
    //清空预览区域显示内容
    config.image_previewText = ' ';
};

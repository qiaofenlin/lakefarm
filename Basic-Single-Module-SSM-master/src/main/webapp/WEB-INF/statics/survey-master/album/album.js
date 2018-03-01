(function (window) {

  // 由于是第三方库，我们使用严格模式，尽可能发现潜在问题
  'use strict';

  function IfeAlbum() {

    // 布局的枚举类型
    this.LAYOUT = {
      PUZZLE: 1,    // 拼图布局
      WATERFALL: 2, // 瀑布布局
      BARREL: 3     // 木桶布局
    };

    // 公有变量可以写在这里
    this.padding = {
      PUZZLE: {
        x: 0,
        y: 0
      },
      WATERFALL: {
        x: 0,
        y: 0
      }, // 瀑布布局
      BARREL: {
        x: 0,
        y: 0
      }
    };
    this.isFullScreen = true;


  }

  // 私有变量可以写在这里
  /**
   * 公共变量，以便获取
   * */
  // 用户设置相册图片间距的变量
  var padding = {
    PUZZLE: {
      x: 0,
      y: 0
    },
    WATERFALL: {
      x: 0,
      y: 0
    }, // 瀑布布局
    BARREL: {
      x: 0,
      y: 0
    }
  };
  //临时缓冲区
  var temp;
  //是否已经全屏
  var isShowed = false;
  //获取需要裁剪图片的样式
  function getClipImgStyle(contain, img) {
    var wdiff = img.width-contain.width*img.height/contain.height;
    var hdiff=0;
    var styles;
    wdiff = parseInt(wdiff);
    if(wdiff<0){//高度不够,以宽度对齐缩放，高度溢出
      hdiff = img.height-contain.height*img.width/contain.width;
      hdiff = parseInt(hdiff);
      var height = img.height*contain.width/img.width;
      hdiff = height-contain.height;
      styles={
        width: contain.width,
        height: height,
        marginTop: -hdiff/2
      }
    }else if(wdiff == 0){//证明比例一致
      styles = {
        width: '100%',
        height: '100%'
      }
    }else{//以相框高度为齐缩放,宽度溢出
      var width = img.width*contain.height/img.height;
      wdiff = width -contain.width;
      styles = {
        width: width,
        height: contain.height,
        marginLeft: -wdiff/2
      }
    }
    return styles;
  }
  //根据对象设置样式
  var setStyles = function (obj, styles) {
    for (var style in styles) {
      obj['style'][style] = styles[style];
    }
  };
  //根据对象设置属性
  var setAttr = function(obj, attrs){
    for (var attr in attrs) {
      obj[attr] = attrs[attr];
    }
  };
  //获取每一个相册
  function getFrames(divs, isFullscreenEnabled){
    return Array.prototype.map.call(divs, function(div){
      if(isFullscreenEnabled){
        div.addEventListener("click", function(e){
          if(e.target.tagName == 'IMG'){
            showPhoto(e.target);
          }
        });
      }

      return div;
    });
  }
  /**
   * 拼图布局的私有方法
   * */
  //获取拼图布局的相册样式
  function getPuzzleFrameStyle(frameObj, nums) {
    var width = frameObj.width;
    var height = frameObj.height;
    console.log(frameObj.width, frameObj.height);
    var innerFrameStyles = []; //套在图片外层的div样式
    var outterFrameStyle = [];//关乎图像布局的div样式
    var frameStyle = { //相册的style
      width: width,
      height: height,
      display: 'flex'
    };
    //计算遮罩效果 相对位置
    var paddingStyles = {};
    var left_width = width/2;
    var right_width = height/2;
    var min_height = height/3;
    if(nums == 1){
      frameStyle.overflow = 'hidden';
    }else if (nums == 2) {
      frameStyle.display = '';
      frameStyle.position = 'relative';

    }else if(nums == 3) {
      left_width = width - height/2;
      paddingStyles = {
        obj1: {
          left: left_width-padding.PUZZLE.y,
          top: 0,
          width: padding.PUZZLE.x*2,
          height: height
        },
        obj2: {
          left: left_width,
          top: height/2 - padding.PUZZLE.x,
          width: height/2,
          height: padding.PUZZLE.x*2
        }
      };
      innerFrameStyles = [{
        width:left_width,
        height: height,
        overflow: 'hidden'
      }, {
        width: right_width,
        height: right_width,
        overflow: 'hidden'
      },{
        width: right_width,
        height: right_width,
        overflow: 'hidden'
      }];
    }else if(nums == 4){
      frameStyle.flexWrap='wrap';
      paddingStyles = {
        obj1: {
          left: left_width-padding.PUZZLE.y,
          top: 0,
          width: padding.PUZZLE.x*2,
          height: height
        },
        obj2: {
          left: 0,
          top: height/2-padding.PUZZLE.x,
          width: width,
          height: padding.PUZZLE.x*2
        }
      };
      innerFrameStyles = {
        width: left_width,
        height: right_width,
        overflow: 'hidden'
      };
    }else if(nums == 5){
      left_width = width*2/3;
      right_width = width/3;
      paddingStyles = {
        obj1: {
          left: 0,
          top: min_height*2 - padding.PUZZLE.x,
          width: left_width,
          height: padding.PUZZLE.x*2
        },
        obj2: {
          left: left_width-padding.PUZZLE.y,
          top: 0,
          width: padding.PUZZLE.y*2,
          height: height
        },
        obj3: {
          left: right_width-padding.PUZZLE.y,
          top: min_height*2,
          width: padding.PUZZLE.y*2,
          height: min_height
        },
        obj4: {
          left: left_width,
          top: right_width - padding.PUZZLE.x,
          width: right_width,
          height: padding.PUZZLE.x*2
        }
      };
      innerFrameStyles=[{
        width: left_width,
        height: min_height*2,
        overflow: 'hidden'
      },{
        width: left_width/2,
        height: min_height,
        overflow: 'hidden'
      },{
        width: left_width/2,
        height: min_height,
        overflow: 'hidden'
      },{
        width: right_width,
        height: width/3,
        overflow: 'hidden'
      },{
        width: right_width,
        height: height-right_width,
        overflow: 'hidden'
      }];
      outterFrameStyle = {
        width: left_width,
        height: min_height,
        display: 'flex'
      };
    }else if(nums == 6){
      left_width = width*2/3;
      right_width = width/3;
      paddingStyles = {
        obj1: {
          left: 0,
          top: min_height*2 - padding.PUZZLE.x,
          width: left_width,
          height: padding.PUZZLE.x*2
        },
        obj2: {
          left: left_width-padding.PUZZLE.y,
          top: 0,
          width: padding.PUZZLE.y*2,
          height: height
        },
        obj3: {
          left: right_width-padding.PUZZLE.y,
          top: min_height*2,
          width: padding.PUZZLE.y*2,
          height: min_height
        },
        obj4: {
          left: left_width,
          top: min_height - padding.PUZZLE.x,
          width: right_width,
          height: padding.PUZZLE.x*2
        },
        obj5: {
          left: left_width,
          top: min_height*2 - padding.PUZZLE.x,
          width: right_width,
          height: padding.PUZZLE.x*2
        }
      };
      innerFrameStyles = [{
        width: left_width,
        height: min_height*2,
        overflow: 'hidden'
      },{
        width: left_width/2,
        height: min_height,
        overflow: 'hidden'
      },{
        width: left_width/2,
        height: min_height,
        overflow: 'hidden'
      },{
        width: right_width,
        height: min_height,
        overflow: 'hidden'
      },{
        width: right_width,
        height: min_height,
        overflow: 'hidden'
      },{
        width: right_width,
        height: min_height,
        overflow: 'hidden'
      }];
      outterFrameStyle = {
        width: left_width,
        height: min_height,
        display: 'flex'
      };
    }

    return {
      innerFrameStyles: innerFrameStyles, //套在图片外层的div样式
      outterFrameStyle: outterFrameStyle, //关乎图像布局的div样式
      frameStyle: frameStyle,              //相册最外层样式
      paddingStyles: paddingStyles        //padding的遮罩效果
    };

  }
  //获取拼图布局照片样式
  function getPuzzleImgStyles(imgs, contain) {

    return Array.prototype.map.call(imgs, function(img, index){
      var container = contain;
      if(imgs.length>2 && imgs.length!=4){
        container = contain[index];
      }
      var imgStyle = getClipImgStyle(container, {
        width: img.naturalWidth,
        height: img.naturalHeight
      });
      if(imgs.length == 2){
        imgStyle.position = 'absolute';
      }

      return imgStyle;
    });
  }
  //根据对象个数确定拼图布局
  var puzzleLayout = function(obj) {//obj相册

    var imgs = obj.children;
    var contain = {
      width: obj.clientWidth,
      height: obj.clientHeight
    };
    var imgStyles;
    var frameStyles;
    var frameStyle;
    var innerObjs;
    var innerFrameStyles;
    var imgPaddings = {};
    if(imgs.length == 1){
      imgStyles = getPuzzleImgStyles(imgs, contain);
      frameStyles = getPuzzleFrameStyle(contain, 1);
      frameStyle = frameStyles.frameStyle;
      innerObjs = {
        obj: {
          innerFrameStyle: '',
          imgStyle: imgStyles[0],
          img: imgs[0]
        }
      };
    }else if(imgs.length == 2){
      var imgAttrs = ['left_tx', 'right_tx'];//img 属性
      imgStyles = getPuzzleImgStyles(imgs, contain);
      frameStyles = getPuzzleFrameStyle(contain, 2);
      frameStyle = frameStyles.frameStyle;
      innerObjs = {
        obj1: {
          img: imgs[0],
          imgAttr: {
            className: imgAttrs[0]
          },
          imgStyle: imgStyles[0]
        },
        obj2: {
          img: imgs[1],
          imgAttr: {
            className: imgAttrs[1]
          },
          imgStyle: imgStyles[0]
        }
      };

    }else if(imgs.length ==3){
      frameStyles = getPuzzleFrameStyle(contain, 3);
      innerFrameStyles = frameStyles.innerFrameStyles;
      frameStyle = frameStyles.frameStyle;
      imgStyles = getPuzzleImgStyles(imgs, innerFrameStyles);
      imgPaddings = frameStyles.paddingStyles;
      innerObjs = {
        obj1: {
          innerFrameStyle: innerFrameStyles[0],
          img: imgs[0],
          imgStyle: imgStyles[0]
        },
        obj2: {
          innerFrameStyle: '',
          innerObj: {
            sameNums: Array.prototype.slice.apply(imgs,[1,3]),
            obj: {
              innerFrameStyle: innerFrameStyles[1],
              imgStyle: imgStyles[1]
            }

          }
        }
      };

    }else if(imgs.length == 4){
      frameStyles = getPuzzleFrameStyle(contain, 4);
      frameStyle = frameStyles.frameStyle;
      innerFrameStyles = frameStyles.innerFrameStyles;
      imgStyles = getPuzzleImgStyles(imgs, innerFrameStyles);
      imgPaddings = frameStyles.paddingStyles;
      innerObjs = {
        sameNums: Array.prototype.slice.apply(imgs,[0,4]),
        obj: {
          innerFrameStyle: innerFrameStyles,
          imgStyle: imgStyles[0]
        }
      };
    }else if(imgs.length == 5){
      frameStyles = getPuzzleFrameStyle(contain, 5);
      frameStyle = frameStyles.frameStyle;
      innerFrameStyles = frameStyles.innerFrameStyles;
      imgStyles = getPuzzleImgStyles(imgs, innerFrameStyles);
      imgPaddings = frameStyles.paddingStyles;
      innerObjs = {
        obj1: {
          innerFrameStyle: '',
          innerObj: {
            obj1: {
              innerFrameStyle: innerFrameStyles[0],
              img: imgs[0],
              imgStyle: imgStyles[0]
            },
            obj2: {
              innerFrameStyle: frameStyles.outterFrameStyle,
              innerObj: {
                sameNums: Array.prototype.slice.apply(imgs,[1,3]),
                obj: {
                  innerFrameStyle: innerFrameStyles[1],
                  imgStyle: imgStyles[1]
                }
              }
            }
          }
        },
        obj2: {
          innerFrameStyle: '',
          innerObj: {
            obj1: {
              innerFrameStyle: innerFrameStyles[3],
              img: imgs[3],
              imgStyle: imgStyles[3]
            },
            obj2: {
              innerFrameStyle: innerFrameStyles[4],
              img: imgs[4],
              imgStyle: imgStyles[4]
            }
          }
        }
      };


    }else if(imgs.length == 6) {
      frameStyles = getPuzzleFrameStyle(contain, 6);
      innerFrameStyles = frameStyles.innerFrameStyles;
      frameStyle = frameStyles.frameStyle;
      imgStyles = getPuzzleImgStyles(imgs, innerFrameStyles);
      imgPaddings = frameStyles.paddingStyles;
      innerObjs = {
        obj1: {
          innerFrameStyle: '',
          innerObj: {
            obj1: {
              innerFrameStyle: innerFrameStyles[0],
              img: imgs[0],
              imgStyle: imgStyles[0]
            },
            obj2: {
              innerFrameStyle: frameStyles.outterFrameStyle,
              innerObj: {
                sameNums: Array.prototype.slice.apply(imgs, [1, 3]),
                obj: {
                  innerFrameStyle: innerFrameStyles[1],
                  imgStyle: imgStyles[1]
                }
              }
            }
          }
        },
        obj2: {
          innerFrameStyle: '',
          innerObj: {
            sameNums: Array.prototype.slice.apply(imgs, [3, 6]),
            obj: {
              innerFrameStyle: innerFrameStyles[3],
              imgStyle: imgStyles[3]
            }
          }
        }
      };
    }
    return {
      innerObjs: innerObjs,   //相册内部所有对象
      frameStyle: frameStyle,  //相框
      imgPaddings: imgPaddings // 相片间距
    };

  }

  // 拼图布局下重塑相册 拼图布局比较复杂
  function puzzleReSet(albumStyles, innerObjs) {
    var frame = document.createElement('DIV');
    setStyles(frame, albumStyles);
    if (innerObjs.sameNums) {
      for (var i = 0; i < innerObjs.sameNums.length; i++) {
        var box = document.createElement('DIV');
        setStyles(box, innerObjs.obj.innerFrameStyle);
        if (innerObjs.obj.attr) {
          setAttr(box, innerObjs.obj.attr);
        }
        if(innerObjs.obj.imgStyle){
          setStyles(innerObjs.sameNums[i], innerObjs.obj.imgStyle);
        }

        box.appendChild(innerObjs.sameNums[i]);

        frame.appendChild(box);
      }
    } else {
      for (var obj in innerObjs) {
        if (innerObjs[obj].innerObj) {//若有子模块递归生成
          var box = puzzleReSet(innerObjs[obj].innerFrameStyle, innerObjs[obj].innerObj);
        } else {
          var box = document.createElement('DIV');
          setStyles(box, innerObjs[obj].innerFrameStyle);//相框属性
          if (innerObjs[obj].attr) {
            setAttr(box, innerObjs[obj].attr);
          }
          if (innerObjs[obj].img) {
            if(innerObjs[obj].imgAttr){
              setAttr(innerObjs[obj].img, innerObjs[obj].imgAttr);
            }
            if(innerObjs[obj].imgStyle){
              setStyles(innerObjs[obj].img, innerObjs[obj].imgStyle);
            }
            box.appendChild(innerObjs[obj].img);
          }
        }
        frame.appendChild(box);
      }
    }
    return frame;
  }
  //设置遮罩效果达到padding
  function setPaddings(frame, paddings) {
    for(var style in paddings) {
      var label = document.createElement('LABEL');
      label.className = 'label_imgPadding';
      setStyles(label, paddings[style]);
      frame.appendChild(label);
    }
  }

  /**
   * 瀑布布局的私有方法
   * */
  function createFallFrame(fallFarme) { //生成框架,默认四列
    var cols = parseInt(fallFarme.className.split(' ')[0].split('_')[1]) || 4;
    var col_width = (fallFarme.offsetWidth - padding.WATERFALL.y*(cols*2 + 1)) / cols;
    var frame = document.createElement('DIV');// 最外层
    var styles = {
      width: fallFarme.clientWidth, // 注意 clientWidth + boderWidth = offsetWidth
      minHeight: fallFarme.clientHeight
    };
    frame.className = 'fall_cols_parent';
    setStyles(frame, styles);
    for(var i=0;i<cols;i++) {//子列
      var item = document.createElement('DIV');
      item.className = 'fall_cols';
      var itemStyle = {
        width: col_width,
        minHeight: styles.minHeight,
        marginLeft: padding.WATERFALL.y,
        marginTop: padding.WATERFALL.y
      };
      if(i === (cols-1)){
        itemStyle.marginRight = padding.WATERFALL.y;
      }
      setStyles(item, itemStyle);
      frame.appendChild(item);
    }
    return {
      frame: frame,
      col_width: col_width
    };
  }
  //获取相册内部对象的样式
  function getPhotoStyles(frame, width) {//style , src
    return Array.prototype.map.call(frame.children, function(img){
      var contain = {
        width: width,
        height: width + Math.random()*100
      };
      var imgWh = {
        width:img.naturalWidth,
        height: img.naturalHeight
      };

      var imgStyles = getClipImgStyle(contain, imgWh);
      return {
        src: img.src,
        style: imgStyles,
        contain: contain
      };
    });

  }



  //得到每一列col内部图片对象的总高度
  function getHight(obj){
    var sum = 0;
    var items = obj.children;
    Array.prototype.forEach.call(items, function(item){
      var height = parseInt(item.style.height.split('px')[0]);
      sum += height;
    });
    return sum;
  }
  //得出即将放入图片的目标区域DOM
  function getFallTarget(frame) {
    var cols = frame.children;
    var res = getHight(cols[0]);
    var node=cols[0];
    for(var i=1;i<cols.length;i++){
      var height = getHight(cols[i]);
      if(res > height){
        res = height;
        node=cols[i];
      }
    }
    return node;
  }
  //为img包装一个div以便计算 最小高度
  function createChildFrame(frameObj, img) {
    var div = document.createElement('DIV');
    if(frameObj.attr){
      setAttr(div, frameObj.attr);
    }
    if(frameObj.styles){
      setStyles(div, frameObj.styles);
    }
    div.appendChild(img);

    return div;
  }

  /**
   * 木桶布局的私有方法
   * */

  //根据宽高比提前分组
  function group(images, option) {
    //将图片分行
    var raws=[];
    var rawWidth=0;
    var rawStart=0;
    var rawEnd=0;
    for(var j=0;j<images.length;j++){
      images[j].height= option.rowHeight;
      images[j].width= option.rowHeight*images[j].ratio;
      rawWidth+=images[j].width;
      rawEnd=j;
      if(rawWidth>option.clientWidth){
        var lastWidth=rawWidth-images[j].width;
        var rawRatio=option.rowHeight/lastWidth;
        var lastHeight=rawRatio*(option.clientWidth);//(rawEnd-rawStart-1)*8
        raws.push({
          start:rawStart,
          end:rawEnd-1,
          height: lastHeight
        });
        rawWidth=images[j].width;
        rawStart=j;
      }
    }
    raws.push({
      start: rawStart,
      end: images.length-1,
      height: option.rowHeight
    });
    return raws;
  }


  /**
   * 实现全屏浏览功能
   * */
  //全屏浏览的显示
  function showPhoto(img) {

    var light = document.getElementsByClassName('white_content')[0];
    var fade = document.getElementsByClassName('black_overlay')[0];
    var width = 0.8*document.body.clientWidth;
    var height =  0.8*document.body.clientHeight;
    var focusStyle = {
      width: width,
      height: height
    };

    if(!isShowed){
      isShowed = true;
      var lightImg = document.createElement('IMG');
      var imgWidth = img.naturalWidth;
      var imgHeight = img.naturalHeight;
      var radio = imgWidth/imgHeight;
      if(radio >1){
        focusStyle.height = 1/radio*0.8*width;
      }else{
        focusStyle.width = radio*0.8*height;
      }
      lightImg.src = img.src;
      setStyles(lightImg, focusStyle);
      setStyles(light, focusStyle);
      fade.style.display = 'block';
      light.appendChild(lightImg);
      light.style.display = 'block';
    } else {
      isShowed = false;
      light.innerHTML = '';
      fade.style.display = 'none';
      light.style.display = 'none';
    }


  }

  //增加全屏浏览遮罩DOM
  function addPhotoShade() {
    var light = document.createElement('DIV');
    var fade = document.createElement('DIV');
    light.className="white_content";
    light.id = 'light';
    fade.className = 'black_overlay';
    fade.id = 'fade';
    light.addEventListener("click", function(e){
      if(e.target.tagName == 'IMG'){
        showPhoto(e.target);
      }
    });
    document.body.appendChild(light);
    document.body.appendChild(fade);

  }


  /************* 以下是本库提供的公有方法 *************/



  /**
   * 初始化并设置相册
   * 当相册原本包含图片时，该方法会替换原有图片
   * @param {(string|string[])} image  一张图片的 URL 或多张图片 URL 组成的数组
   * @param {object}            option 配置项
   */
  IfeAlbum.prototype.setImage = function (images, option) {//每次执行设置一次相册

    if (typeof images === 'string') {
      // 包装成数组处理
      this.setImage([images]);
      return;
    }

    // 实现拼图布局
    if(option.type == 'PUZZLE'){
      //var puzzleNewLayouts = puzzleLayout(images);//根据img_frame生成布局
      var newFrame = puzzleReSet(images.frameStyle, images.innerObjs);//根据布局重新生成frame_dom
      temp.innerHTML = '';
      temp.appendChild(newFrame);
      setPaddings(temp, images.imgPaddings);//设置遮罩-图片padding
    }else if(option.type == 'WATERFALL'){
      //创建瀑布基本布局
      var newFallFrame = createFallFrame(images);
      //计算每张图片的样式
      temp = newFallFrame.frame;
      var imgStyles = getPhotoStyles(images, Math.floor(newFallFrame.col_width));

      var imgObjs = imgStyles.map(function(img){
        var imgDom = document.createElement('IMG');
        imgDom.src = img.src;
        setStyles(imgDom, img.style);
        img.contain.marginBottom = padding.WATERFALL.x;
        var childFrame = createChildFrame({
          styles: img.contain
        }, imgDom);

        return childFrame;
      });
      this.LAYOUT.WATERFALL[option.index] = imgObjs;//根据相册index替换原有的img
      return imgObjs;
    }else if (option.type == 'BARREL'){

    }

  };

  /**
   * 初始化并设置所有含有指定className的相册
   * 当相册原本包含图片时，该方法会替换原有图片
   */
  IfeAlbum.prototype.run = function () {//每次执行设置一次相册
    addPhotoShade();//添加事件
    // 实现拼图布局
    var _this = this;
    this.setLayout();
    var layouts = this.getLayout();
    var puzzles = layouts.PUZZLE;//所有相册
    var falls = layouts.WATERFALL;
    var buckets = layouts.BARREL;

    puzzles.forEach(function(frame){
      temp = frame;//把frame放入临时区
      var puzzleNewLayouts = _this.addImage(frame, {type: 'PUZZLE'});//生成布局
      _this.setImage(puzzleNewLayouts, {type: 'PUZZLE'});//重新设置照片
    });
    falls.forEach(function(frame, index){
      var option = {
        type: 'WATERFALL',
        index: index
      };
      var imgObjs = _this.setImage(frame, option);
      //生成和添加图片
      _this.addImage(imgObjs, option);
      //替换原有相册的子元素
      frame.innerHTML = '';
      frame.appendChild(temp);

    });
    buckets.forEach(function(bucket){
      var images = _this.getImageDomElements(bucket);
      temp = bucket;
      var groups = group(images, {
        clientWidth: bucket.clientWidth,
        rowHeight: 70
      });
      bucket.innerHTML = '';
      _this.addImage(images, {
        groups: groups,
        type: 'BARREL'
      });
    });


  };

  /**
   * 获取相册所有图像对应的 DOM 元素
   * 可以不是 ，而是更外层的元素
   * @return {HTMLElement[]} 相册所有图像对应的 DOM 元素组成的数组
   */
  IfeAlbum.prototype.getImageDomElements = function(frame) {
    //拼图布局处理
    var imgs = frame.children;
    return Array.prototype.map.call(imgs, function(img){
      console.log(img,img.naturalWidth, img.naturalHeight, img.naturalWidth/img.naturalHeight);
      var ratio = img.naturalWidth/img.naturalHeight;//获取为空的情况
      if(!ratio){
        ratio = 1;
      }
      return {
        width: img.naturalWidth,
        height: img.naturalHeight,
        ratio: ratio,
        src: img.src
      }
    });

  };



  /**
   * 向相册添加图片
   * 在拼图布局下，根据图片数量重新计算布局方式；其他布局下向尾部追加图片
   * @param {(string|string[])} image 一张图片的 URL 或多张图片 URL 组成的数组
   */
  IfeAlbum.prototype.addImage = function (image, option) {
    //根据imgs计算样式
    if(option.type == 'PUZZLE'){
      var puzzleNewLayouts = puzzleLayout(image);//根据img_frame生成布局
      return puzzleNewLayouts;
    }else if(option.type=='WATERFALL'){
      image.forEach(function(img){
        var target = getFallTarget(temp);
        target.appendChild(img);
      });
    }else if(option.type = 'BARREL'){
      option.groups.forEach(function(group){
        var nums = group.end - group.start +1;
        var interval = padding[option.type].x;
        var lastInterval = (nums-1)*interval/nums;
        console.log(lastInterval);
        for(var i=group.start;i<=group.end;i++){
          var img = document.createElement('IMG');
          var style = {
            width: Math.floor(image[i].ratio*group.height-lastInterval)-0.5,//不知道为什么总有误差
            height: Math.floor(group.height),
            marginBottom: padding[option.type].y
          };
          if(i!=group.end){
            style.marginRight = interval;
          }
          setStyles(img, style);
          img.src = image[i].src;
          temp.appendChild(img);
        }
      });
    }

  };



  /**
   * 移除相册中的图片
   * @param  {(HTMLElement|HTMLElement[])} image 需要移除的图片
   * @return {boolean} 是否全部移除成功
   */
  IfeAlbum.prototype.removeImage = function (image) {

  };



  /**
   * 设置相册的布局
   * @param {number} layout 布局值，IfeAlbum.LAYOUT 中的值
   */
  IfeAlbum.prototype.setLayout = function () {//存储相册所有信息
    //type == 'PUZZLE' || type == 'WATERFALL' || type == 'BARREL'
    for(var key in this.LAYOUT){
      if(key == 'PUZZLE'){
        var divs = document.getElementsByClassName('puzzle');
        this.LAYOUT[key] = getFrames(divs, this.isFullscreenEnabled);

      }else if(key == 'WATERFALL'){
        this.LAYOUT[key] = getFrames(document.querySelectorAll('div[class^="waterfall"]'), this.isFullscreenEnabled);

      }else if(key == 'BARREL'){
        var buckets = document.getElementsByClassName('barrel');
        this.LAYOUT[key] = getFrames(buckets, this.isFullscreenEnabled);
      }
    }


  };



  /**
   * 获取相册的布局
   * @return {number} 布局枚举类型的值
   */
  IfeAlbum.prototype.getLayout = function() {
    return this.LAYOUT;
  };



  /**
   * 设置图片之间的间距
   * 注意这个值仅代表图片间的间距，不应直接用于图片的 margin 属性，如左上角图的左边和上边应该紧贴相册的左边和上边
   * 相册本身的 padding 始终是 0，用户想修改相册外框的空白需要自己设置相框元素的 padding
   * @param {string}  type 类型
   * @param {number}  x  图片之间的横向间距
   * @param {number} [y] 图片之间的纵向间距，如果是 undefined 则等同于 x
   */
  IfeAlbum.prototype.setGutter = function (type, x, y) {
    console.log(arguments);
    if(typeof x != 'number'){
      return;
    }
    if(!y || typeof y!='number'){
      y = x;
    }
    if(type == 'PUZZLE' || type == 'WATERFALL' || type == 'BARREL'){
      padding[type].x = x;
      padding[type].y = y;
    }
  };



  /**
   * 允许点击图片时全屏浏览图片
   */
  IfeAlbum.prototype.enableFullscreen = function () {
    this.isFullScreen = true;
  };



  /**
   * 禁止点击图片时全屏浏览图片
   */
  IfeAlbum.prototype.disableFullscreen = function () {
    this.isFullScreen = false;
  };



  /**
   * 获取点击图片时全屏浏览图片是否被允许
   * @return {boolean} 是否允许全屏浏览
   */
  IfeAlbum.prototype.isFullscreenEnabled = function () {
    return this.isFullScreen;
  };


  /**
   * 设置木桶模式每行图片数的上下限
   * @param {number} min 最少图片数（含）
   * @param {number} max 最多图片数（含）
   */
  IfeAlbum.prototype.setBarrelBin = function (min, max) {

    // 注意异常情况的处理，做一个健壮的库
    if (min === undefined || max === undefined || min > max) {
      console.error('...');
      return;
    }

    // 你的实现

  };



  /**
   * 获取木桶模式每行图片数的上限
   * @return {number} 最多图片数（含）
   */
  IfeAlbum.prototype.getBarrelBinMax = function () {

  };



  /**
   * 获取木桶模式每行图片数的下限
   * @return {number} 最少图片数（含）
   */
  IfeAlbum.prototype.getBarrelBinMin = function () {

  };



  /**
   * 设置木桶模式每行高度的上下限，单位像素
   * @param {number} min 最小高度
   * @param {number} max 最大高度
   */
  IfeAlbum.prototype.setBarrelHeight = function (min, max) {

  };



  /**
   * 获取木桶模式每行高度的上限
   * @return {number} 最多图片数（含）
   */
  IfeAlbum.prototype.getBarrelHeightMax = function () {

  };



  /**
   * 获取木桶模式每行高度的下限
   * @return {number} 最少图片数（含）
   */
  IfeAlbum.prototype.getBarrelHeightMin = function () {

  };



  // 你想增加的其他接口



  /************* 以上是本库提供的公有方法 *************/



  // 实例化
  if (typeof window.ifeAlbum === 'undefined') {
    // 只有当未初始化时才实例化
    window.ifeAlbum = new IfeAlbum();
   

  }

}(window));
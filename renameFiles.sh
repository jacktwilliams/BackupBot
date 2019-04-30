#!/bin/bash
cp /home/jack/Downloads/*.zip /home/jack/BbotPlayground/ #copy zips over
cp /home/jack/Downloads/*.jpg /home/jack/BbotPlayground/
cp /home/jack/Downloads/*.png /home/jack/BbotPlayground/
cp /home/jack/Downloads/*.pptx /home/jack/BbotPlayground/

rename 's/.zip/-test.zip/g' *.zip
rename 's/.png/-test.png/g' *.png
rename 's/.jpg/-test.jpg/g' *.jpg
rename 's/.pptx/-test.pptx/g' *.pptx

sourceanalyzer -b sb1.1 -clean

sourceanalyzer -b sb1.1 -source 1.5  -cp /lib/*.jar /src/**/*.java

sourceanalyzer -b sb1.1 -scan -f swingbean.fpr

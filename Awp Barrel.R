f = function(x){

  return (2/(x+0.2))
}

g = function(x,height){
  out = c()
  for (i in x){
    num = as.numeric((integrate(f,0,i)[1]))
    out = c(out,num)
  }
  return (out)
}

height = 1.11111


X = seq(0,28.75,0.002)
a = g(X,height) %% height


plot(X,a,xlim=c(0,28.75),ylim=c(0,28.75))
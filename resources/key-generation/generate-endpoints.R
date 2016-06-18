##########
##
##    generate-endpoints.R
##    Thomas in't Veld, 2016-06-18
##    script to generate the endpoints ClojureScript construct for SnailFinder app
##
##    run like:
##    Rscript generate-endpoints.R
##
##    make sure endpoints.csv is the straight dump from our SnailFinder google doc
##
##    
##
###########


rm(list=ls())
df <- read.csv('endpoints.csv', stringsAsFactors = FALSE)

# first some munging to get format of each column exactly right!
df$node.ID <- tolower(df$node.ID)
df$node.ID <- paste(':', df$node.ID, sep='')

# :s1  {:name                      ""
#       :common-name               ""
#       :family                    ""
#       :size                      ""
#       :image-white               ""
#       :image-shell               ""
#       :image-habitat             ""
#       :identification-characters ""
#       :similar-species           ""
#       :ecology                   ""
#       :gb-distribution           ""
#       :world-distribution        ""
#       :conservation-status       ""
#       :map-image                 ""
#       :notes                     ""}

printEndpoint <- function(xr){
  if(is.na(xr$notes)){xr$notes<-''}
  if(is.na(xr$image.1..animals.on.white.)){xr$image<-''}
  if(is.na(xr$map.image)){xr$map.image<-''}
  string <- paste(
    xr$node.ID, ' { ',
    ':name "', xr$name, '" ',
    ':common-name "', xr$common.name, '" ',
    ':family "', xr$family, '" ',
    ':size "', xr$size, '" ',
    ':image-white "', xr$image.1..animals.on.white., '" ',
    ':image-shell "', xr$image.2..shells., '" ',
    ':image-habitat "', xr$image.3..animals.in.habitat., '" ',
    ':identification-characters "', xr$identification.characters, '" ',
    ':similar-species "', xr$similar.species, '" ',
    ':ecology "', xr$ecology, '" ',
    ':gb-distribution "', xr$distribution.in.the.British.Isles, '" ',
    ':world-distribution "', xr$distribution.elsewhere, '" ',
    ':conservation-status "', xr$conservation.status, '" ',
    ':map-image "', xr$map.image, '" ',
    ':notes "', xr$notes, '"}', sep=''
  )
  return(string)
}

endpointsStrings <- sapply(1:nrow(df), function(i) return(printEndpoint(df[i,])))

resultFrame <- c('(def snails {', endpointsStrings, '})')
write(resultFrame, file='../snails-out.txt')
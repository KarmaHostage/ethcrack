Ethcrack
===

> Recover your forgotten mist-wallet password

##Usage

```
usage: ethcrack
 -f,--wordlist <default: wordlist.txt>   use a wordlist to recover your
                                         password
 -h,--help                               print this help
 -p,--password <arg>                     test specific password
 -w,--wallet <default: wallet.json>      mist wallet file
```

for presale wallets, consider using https://github.com/ryepdx/pyethrecover

###Disclaimer

Mist wallets use scrypt algorithms, which are quite slow. Recovering your password might take a long time, but it might be your only hope.

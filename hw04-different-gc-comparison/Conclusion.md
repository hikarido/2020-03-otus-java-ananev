# Enviroment

```
lshw -short
H/W path     Device   Class          Description
================================================
                      system         Computer
/0                    bus            Motherboard
/0/0                  memory         15GiB System memory
/0/1                  processor      Intel(R) Core(TM) i3-7100 CPU @ 3.90GHz
/0/100                bridge         Xeon E3-1200 v6/7th Gen Core Processor Host Bridge/DRAM Registers
/0/100/2              display        HD Graphics 630
/0/100/8              generic        Xeon E3-1200 v5/v6 / E3-1500 v5 / 6th/7th Gen Core Processor Gaussian Mixture
/0/100/14             bus            100 Series/C230 Series Chipset Family USB 3.0 xHCI Controller
/0/100/14.2           generic        100 Series/C230 Series Chipset Family Thermal Subsystem
/0/100/16             communication  100 Series/C230 Series Chipset Family MEI Controller #1
/0/100/17             storage        Q170/Q150/B150/H170/H110/Z170/CM236 Chipset SATA Controller [AHCI Mode]
/0/100/1c             bridge         100 Series/C230 Series Chipset Family PCI Express Root Port #5
/0/100/1c/0  enp1s0   network        RTL8111/8168/8411 PCI Express Gigabit Ethernet Controller
/0/100/1f             bridge         H110 Chipset LPC/eSPI Controller
/0/100/1f.2           memory         Memory controller
/0/100/1f.3           multimedia     100 Series/C230 Series Chipset Family HD Audio Controller
/0/100/1f.4           bus            100 Series/C230 Series Chipset Family SMBus
```

# Table of results

|           GC            | -Xmx256M | -Xmx8G |
|-------------------------|--------------|--------------|
|-XX:+UseSerialGC         |     Up time: 9911 ms, Copy: 4 times, 115 ms per time; MarkSweepCompact: 0 times, 0 ms per time        |      Up time: 14 min , Copy: 7279 times,519 ms per time; MarkSweepCompact: 50 times,7939  ms per time        |
|-XX:+UseParallelGC       |     Up time: 14152 ms, Scavenge: 4 times, 85 ms per time; MarkSweepCompact: 5 times, 295 ms per time     |   Up time:  2500 sec and isn't OOM, Scavenge:  136 times, 241 ms per time; MarkSweepCompact: 24 times, 5633 ms per time              |
|-XX:+UseParallelOldGC    |     Up time: 9457 ms, Scavenge: 4 times, 60 ms per time; MarkSweepCompact: 2 times, 208 ms per time     |   Up time:  17 min and isn't OOM, Scavenge: 87  times, 374 ms per time; MarkSweepCompact: 90 times, 1100 ms per time              |
|-XX:+UseConcMarkSweepGC  |     Up time 13 sec, ParNew: 5 times, 85 ms per time; ConcurrentMarkSweep: 7 times, 476 ms per time |       Up time 12 min, ParNew: 118 times, 148 ms per time; ConcurrentMarkSweep: 35 times, 10011 ms per time       |
|-XX:+UseG1GC             |     Up time 16928 ms, Young Generation: 21 times, 41 ms per time; Old Generation: 6 times, 244 ms per time | Up time 8 min, Young Generation: 53 times, 173 ms per time; Old Generation: 5 times, 10 ms per time

* If computer has 16G of memory, for example others running processes consume 5G, and we run -Xmx10G, OS eventually kill our jvm... Write in log "Killed" and end. Because of this we use -Xmx8G as maximum of jvm memory. My PC consumes exactly 5G of memory all time.
* All test fall with OOM on 256M of memory. 

# Notes
* +UseSerialGC  - then more garbage in memory them longer collection time. Collection time starts at 150ms grows non-liner to 8 sec. When program is near to OutOfMemory Collector works nonstop and can't get free memory (See plateau at highest point of [memory usage](./images/UseSerialGC_8G.png) graphic). As his name implies it uses only one core for collection purpose.
* +UseParallelGC - utilize all CPU on my 4cpu machine. Then more garbage in memory them longer collection time, but collection time itself shorter than in SerialGC and varies from 120 ms till 6 sec. When program is near to OutOfMemory Collector works nonstop and can't get free memory (See plateau at highest point of [memory usage](./images/UseParallelGC_8G.png) graphic).
    * but OOM is not happen. Application just makes full pauses for collection
    * eventually process was killed by Ctrl + C
```
[2123,037s][info][gc] GC(407) Pause Full (Ergonomics) 5493M->5493M(5561M) 6433,157ms
[2129,570s][info][gc] GC(408) Pause Full (Ergonomics) 5493M->5493M(5561M) 6531,746ms
[2135,932s][info][gc] GC(409) Pause Full (Ergonomics) 5493M->5493M(5561M) 6361,628ms
[2142,355s][info][gc] GC(410) Pause Full (Ergonomics) 5493M->5493M(5561M) 6423,458ms
[2151,285s][info][gc] GC(411) Pause Full (Ergonomics) 5493M->5493M(5561M) 8929,206ms
[2157,532s][info][gc] GC(412) Pause Full (Ergonomics) 5493M->5493M(5561M) 6246,637ms
[2164,198s][info][gc] GC(413) Pause Full (Ergonomics) 5493M->5493M(5561M) 6666,632ms
[2171,064s][info][gc] GC(414) Pause Full (Ergonomics) 5493M->5493M(5561M) 6865,655ms
[2177,596s][info][gc] GC(415) Pause Full (Ergonomics) 5493M->5493M(5561M) 6531,690ms
[2184,308s][info][gc] GC(416) Pause Full (Ergonomics) 5493M->5493M(5561M) 6711,769ms
[2190,799s][info][gc] GC(417) Pause Full (Ergonomics) 5493M->5493M(5561M) 6490,041ms
[2197,018s][info][gc] GC(418) Pause Full (Ergonomics) 5493M->5493M(5561M) 6218,284ms
[2203,321s][info][gc] GC(419) Pause Full (Ergonomics) 5493M->5493M(5561M) 6303,172ms
[2209,880s][info][gc] GC(420) Pause Full (Ergonomics) 5493M->5493M(5561M) 6558,301ms
[2216,375s][info][gc] GC(421) Pause Full (Ergonomics) 5493M->5493M(5561M) 6494,746ms
[2222,827s][info][gc] GC(422) Pause Full (Ergonomics) 5493M->5493M(5561M) 6452,155ms
[2229,237s][info][gc] GC(423) Pause Full (Ergonomics) 5493M->5493M(5561M) 6409,180ms
[2235,545s][info][gc] GC(424) Pause Full (Ergonomics) 5493M->5493M(5561M) 6307,818ms
[2241,755s][info][gc] GC(425) Pause Full (Ergonomics) 5493M->5493M(5561M) 6210,335ms
```

* +UseParallelOldGC - similar to ParallelGC but pauses a 
little bit shorter. Collection time grows more smooth from 60ms till 6100 ms. Up time longer than a previous GC.
* +UseConcMarkSweepGC - First parallel GC which fell with OOM. Before OOM can do pause length till 20 sec.
* +UseG1GC - Old generation not presences long time. Only when app has reached 8GB memory usage old generation appears.
And then app fell with OOM.

# Conclusions
Mostly based on [documentation](https://www.oracle.com/technetwork/java/javase/tech/memorymanagement-whitepaper-1-150020.pdf) 
and [habr](https://habr.com/ru/post/269621/)
* SerialGC - for old system and weak client system(Tetris game on tetris hardware?)
* ParallelGC - appropriate for strong servers for batch data processing(Reports processing?)
* ParallelOldGC - appropriate for pause time restricted application (Video processing?)
* ConcMarkSweepGC - appropriate for application which has no time pause restriction, run on many sockets system and 
store many long living objects.(Web server?). But my Idea instance uses this GC type.
* G1GC - Apps which has a large heap. Heap size must be more than 6G and pauses at most 0.5 sec are permitted. 
Throughout testing, I came to conclusion that G1 can be used when many young objects are create. (Big data?)

# PS
Manuals told us - tune GC only when it is need and remember about ergonomics.
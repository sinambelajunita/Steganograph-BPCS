# Steganograph-BPCS
Steganograph using BPCS
Format citra yang diguankan dapat dipilih salah satu dari dua berikut: BMP (bitmap) atau PNG (Portable Network Graphics). Format BMP tidak terkompresi, sedangkan format PNG terkompresi dengan metode kompresi lossless. Jika anda memilih format PNG, maka akan ada bonus nilai = 20.

Pada praktekmya, sebelum disisipkan, pesan dienkripsi terlebih dahulu dengan sebuah algoritma enkripsi. Karena anda baru belajar algoritma kriptografi klasik, maka algoritma enkripsi yang digunakan adalah Vigenere Cipher (extended untuk alfabet 256 karakter) seperti yang pernah dikerjakan pada Tucil 1. Pesan yang disisipkan adalah sembarang file dengan ukuran yang tidak melebihi kapasitas penyisipan (payload). Kapasitas penyisipan dihitung sebelum proses penyisipan.

Spesifkasi program:
1.	Program menerima masukan berupa citra digital dengan format BMP atau PNG, nama file pesan, dan kunci steganografi.
2.	Selain masukan di atas, parameter threshold pada metode BPCS juga menjadi salah satu masukan.
3.	Pengguna dapat memilih apakah pesan dienkripsi atau tidak dienkripsi sebeleum disisipkan.
4.	Pengguna memasukkan sebuah kata kunci (maksimal 25 karakter) yang berfungsi dua: sebagai kunci enkripsi pada Vigenere Cipher dan sebagai kunci (seed) pembangkitan bilangan acak.
Contoh: Kunci = ’STEGANO’, kunci ini langsung dijadikan sebagai kunci enkripsi.
Untuk seed  berupa bilangan acak (yang umumnya berupa integer/real), maka nilai-nilai integer dari string ’STEGANO’ dijumlahkan, yaitu Int(’S’) + Int(’T’) + Int(’E’) + Int(’G’) + Int(’A’) + Int(’N’) + Int(’O’) = ... 
Atau, hanya mengambil sebagian huruf dari STEGANO, misalnya karakter pada posisi ganjil saja, yaitu Int(’S’) + Int(’E’) + Int(’A’) + Int(’O’) = ...., atau terserah cara yang anda gunakan.
5.	Jangan menyisipkan kunci di dalam file citra.
6.	Program menolak menyisipkan pesan jika ukuran file pesan melebihi payload. 
7.	Program dapat menyimpan stego-image (citra yang sudah disisipi pesan)..
8.	Program dapat mengekstraksi pesan utuh seperti sediakala dan menyimpannya sebagai file dengan nama lain (save as).
9.	Agar format file hasil ekstraksi diketahui, maka properti file seperti ekstensi (.exe, .doc, .pdf, dll), sebaiknya juga disimpan (atau nama file asli juga disimpan.agar diketahui formatnya, sehingga ketika di-save as yang muncul adalah nama file asli tersebut, lalu pengguna dapat menggantinya dengan nama lain). Penyimpanan nama file (dan properti lainnya) tentu akan mengurangi kapasitas pesan yang dapat disimpan.
10.	Program dapat menampilkan (view) citra asli dan citra stegano dalam dua jendela berbeda.
11.	Program dapat menampilkan ukuran kualitas citra hasil steganografi dengan PSNR (Peak Signal- to-Noise Ratio). PSNR adalah metrik yang umum digunakan untuk mengukur kualitas citra. PSNR dihitung dengan rumus:
 						(II.13)


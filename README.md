# Kopi Nyemil

## Deskripsi Aplikasi
**Kopi Nyemil** adalah aplikasi mobile berbasis Android yang dirancang untuk memudahkan pengguna dalam memesan berbagai varian kopi dan camilan favorit. Aplikasi ini dibuat sebagai simulasi platform pemesanan *Food and Beverage* yang memiliki antarmuka pengguna (UI) modern, interaktif, dan responsif. Tujuan dari aplikasi ini adalah untuk mendemonstrasikan penerapan Jetpack Compose dalam membangun UI deklaratif serta pengelolaan *state* (State Management) yang efisien tanpa menggunakan XML.

## Fitur Aplikasi
1. **Splash / Welcome:** Layar sapaan awal saat aplikasi pertama kali dibuka.
2. **Sign Up (Pilihan):** Layar yang memberikan opsi bagi pengguna baru untuk masuk atau mendaftar.
3. **Login:** Halaman autentikasi untuk pengguna yang sudah memiliki akun.
4. **Register:** Formulir pendaftaran akun bagi pengguna baru.
5. **Home:** Beranda utama yang menampilkan nama pengguna, sapaan, serta akses ke menu utama.
6. **Daftar Makanan dan Minuman:** Katalog menu (seperti Donat Cokelat, Cappuccino, dll) lengkap dengan harga dan tombol untuk menambahkan ke keranjang pesanan.
7. **Profile:** Halaman yang menampilkan informasi data diri pengguna (nama, email, no HP) dengan fitur edit dan tombol logout.
8. **Alamat Pengiriman:** Formulir pemesanan yang merangkum item keranjang serta meminta input nama penerima, alamat detail, dan patokan lokasi.
9. **Konfirmasi Pesanan:** Layar ringkasan akhir setelah pesanan dibuat yang menampilkan ucapan sukses, ID pesanan, dan total harga.

## Teknologi yang Digunakan
1. **Kotlin:** Bahasa pemrograman utama yang digunakan.
2. **Android Studio:** *Integrated Development Environment* (IDE) resmi untuk pengembangan Android.
3. **Jetpack Compose:** *Toolkit* modern Android untuk membangun UI secara deklaratif.
4. **Material Design (Material 3):** Panduan dan komponen desain dari Google untuk memastikan UI yang konsisten dan elegan.
5. **State Management:** Pengelolaan *state* aplikasi menggunakan `ViewModel` dan `StateFlow` untuk memastikan UI selalu sinkron dengan data.
6. **Data Dummy:** Aplikasi menggunakan data statis/lokal (tanpa koneksi internet atau backend) untuk keperluan demonstrasi.

## Struktur Project
Struktur utama pada direktori source code (`app/src/main/java/com/example/`):
- `MainActivity.kt`: *Entry point* (titik awal) aplikasi yang mengatur navigasi antar layar (Screen).
- `data/SampleData.kt`: Berisi data statis/dummy untuk daftar produk (makanan & minuman) dan data user default.
- `model/`: Berisi *data classes* yang merepresentasikan entitas (misal: `User`, `ProductItem`, `OrderSummary`, dll).
- `ui/components/`: Kumpulan komponen UI yang dapat digunakan kembali (*reusable components*), seperti tombol (`KopiPrimaryButton`), text field, card produk, dan *bottom navigation bar*.
- `ui/screens/`: Berisi file *Composable* untuk masing-masing layar aplikasi (Home, Login, Profile, Order, dll).
- `ui/theme/`: Konfigurasi tema aplikasi, warna (*Color*), tipografi (*Type*), dan pembentukan *Theme* (MaterialTheme).
- `viewmodel/KopiNyemilViewModel.kt`: *Class* yang bertanggung jawab mengelola logika bisnis, menyimpan state (seperti keranjang belanja dan data login), serta menangani *event* dari UI.

## Cara Menjalankan Aplikasi
1. Buka **Android Studio**.
2. Pilih menu **File > Open**, lalu arahkan ke folder root project `kopi-nyemil` (C:/Users/LENOVO/Downloads/kopi-nyemil) dan klik **OK**.
3. Tunggu hingga proses **Gradle Sync** selesai (biasanya ditandai dengan berhentinya loading bar di bagian bawah layar).
4. Pastikan Anda telah membuat *Android Virtual Device* (AVD) melalui Device Manager atau telah menyambungkan perangkat fisik Android dengan mode *USB Debugging* aktif.
5. Pilih perangkat target di panel *toolbar* atas.
6. Klik tombol **Run (ikon panah hijau/play)** atau tekan `Shift + F10` pada keyboard untuk melakukan *build* dan menjalankan aplikasi.

> **Catatan:**
> Aplikasi ini sepenuhnya menggunakan data statis dari dalam memori aplikasi (lokal). Aplikasi ini tidak terhubung ke API eksternal dan **tidak memerlukan koneksi database atau backend** untuk dapat berjalan. Semua transaksi dan perubahan data akan ter-reset ketika aplikasi ditutup (*kill process*).

## Akun Demo / Sample Data
Untuk mencoba login ke dalam aplikasi tanpa perlu melakukan registrasi, Anda dapat menggunakan salah satu akun demo berikut:

**Akun Budi:**
- **Username:** `budilagiaje`
- **Password:** `123`

**Akun Siti:**
- **Username:** `kopilover`
- **Password:** `ngopiheula`


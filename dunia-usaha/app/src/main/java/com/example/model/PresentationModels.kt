package com.example.model

enum class SlideDestination(
    val pageNumber: Int,
    val title: String,
    val section: String
) {
    COVER(1, "Sampul Dunia Usaha", "Pembuka"),
    HALLO(2, "Hallo! Selamat Datang", "Pembuka"),
    KENALAN(3, "Ayo Kita Kenalan", "Pembuka"),
    MENU(4, "Menu Utama", "Navigasi"),
    PETUNJUK(5, "Petunjuk Penggunaan", "Panduan"),
    PROFIL_TIM(6, "Profil Pengembang", "Profil"),
    PROFIL_DOSEN(7, "Dosen Pengampu", "Profil"),
    CAPAIAN(8, "Capaian Pembelajaran", "Tujuan"),
    TUJUAN(9, "Tujuan Pembelajaran", "Tujuan"),
    MATERI_MENU(10, "Menu Materi", "Materi"),
    MATERI_WIRAUSAHA(11, "Apa Itu Wirausaha?", "Materi"),
    MATERI_SIKAP(12, "Sikap Wirausahawan", "Materi"),
    MATERI_KOSAKATA(13, "Kosakata dan Idiom", "Materi"),
    MATERI_IDE_POKOK(14, "Ide Pokok & Teks Informatif", "Materi"),
    MATERI_WAWANCARA(15, "Wawancara dan Laporan", "Materi"),
    TAHUKAH_KAMU(16, "Tahukah Kamu?", "Materi"),
    KESIMPULAN(17, "Kesimpulan", "Materi"),
    EVALUASI_START(18, "Evaluasi Pembelajaran", "Evaluasi"),
    SOAL_1(19, "Soal 1", "Evaluasi"),
    SOAL_2(20, "Soal 2", "Evaluasi"),
    SOAL_3(21, "Soal 3", "Evaluasi"),
    SOAL_4(22, "Soal 4", "Evaluasi"),
    EVALUASI_HASIL(23, "Hebat! Kamu Berhasil", "Evaluasi"),
    REFLEKSI(24, "Penilaian Aplikasi", "Penutup"),
    SCAN_QR(25, "Scan QR Code", "Penutup"),
    DAFTAR_PUSTAKA(26, "Daftar Pustaka", "Penutup"),
    GAME_ARENA(27, "Games Pasar Cilik", "Games");

    companion object {
        val presentationSlides: List<SlideDestination> = entries.filter { it != GAME_ARENA }
    }
}

enum class TransitionStyle(val label: String) {
    SLIDE_SCALE("Geser & Skala"),
    FADE_ZOOM("Zoom Halus"),
    BOOK_FLIP("Buka Halaman")
}

data class DeveloperProfile(
    val name: String,
    val kelas: String,
    val nim: String,
    val hijabColorHex: Long,
    val blazerColorHex: Long
)

data class VocabItem(
    val term: String,
    val meaning: String,
    val example: String
)

data class IdiomItem(
    val idiom: String,
    val meaning: String,
    val example: String
)

data class QuizOption(
    val letter: String,
    val text: String
)

data class QuizQuestion(
    val number: Int,
    val readingText: String? = null,
    val question: String,
    val options: List<QuizOption>,
    val correctLetter: String,
    val explanation: String
)

enum class AppRating(val label: String) {
    SERU("Seru"),
    KURANG("Kurang"),
    TIDAK_BAGUS("Tidak Bagus")
}

data class GameIdiomRound(
    val promptIdiom: String,
    val sentenceHint: String,
    val choices: List<String>,
    val correctMeaning: String
)

object DuniaUsahaContent {
    val developers = listOf(
        DeveloperProfile(
            name = "Arina Aulia Ramadhani",
            kelas = "BC24.1",
            nim = "240407512004",
            hijabColorHex = 0xFFFFFFFF,
            blazerColorHex = 0xFFF5B400
        ),
        DeveloperProfile(
            name = "Andi Aisya Putri Andini",
            kelas = "BC24.1",
            nim = "240407511015",
            hijabColorHex = 0xFF1E293B,
            blazerColorHex = 0xFFF5B400
        ),
        DeveloperProfile(
            name = "Hilda Cahyani",
            kelas = "BC24.1",
            nim = "240407512007",
            hijabColorHex = 0xFF1D4E89,
            blazerColorHex = 0xFFF8FAFC
        )
    )

    val learningObjectives = listOf(
        "Menjelaskan pengertian dan contoh kegiatan wirausaha dengan benar.",
        "Mengidentifikasi kosakata dan idiom yang berkaitan dengan kegiatan usaha.",
        "Menentukan ide pokok dan informasi penting dari teks informatif.",
        "Membedakan kalimat efektif dan tidak efektif.",
        "Menyusun pertanyaan wawancara sederhana kepada seorang wirausahawan.",
        "Menyampaikan hasil wawancara secara lisan dengan bahasa yang santun."
    )

    val entrepreneurExamples = listOf(
        "Berjualan makanan dan minuman.",
        "Membuat dan menjual kerajinan tangan.",
        "Membuka toko kecil.",
        "Menjual tanaman hias.",
        "Membuat kue untuk dijual."
    )

    val entrepreneurAttitudes = listOf(
        "Jujur" to "Tidak berbohong kepada pembeli.",
        "Disiplin" to "Datang dan membuka usaha tepat waktu.",
        "Kreatif" to "Mampu membuat produk yang menarik.",
        "Kerja keras" to "Tidak mudah menyerah dalam menjalankan usaha.",
        "Bertanggung jawab" to "Menjaga kualitas barang yang dijual.",
        "Ramah" to "Melayani pembeli dengan sopan.",
        "Mandiri" to "Berusaha melakukan pekerjaan dengan kemampuan sendiri."
    )

    val vocabList = listOf(
        VocabItem("Modal", "Uang atau barang untuk memulai usaha", "Siti menyiapkan modal Rp20.000 untuk membeli tepung dan gula."),
        VocabItem("Produk", "Barang yang dihasilkan atau dijual", "Kue klepon dan onde-onde adalah produk buatan Bu Siti."),
        VocabItem("Konsumen", "Orang yang membeli atau menggunakan produk", "Konsumen senang berbelanja jika penjual bersikap ramah."),
        VocabItem("Keuntungan", "Hasil lebih dari kegiatan usaha setelah dikurangi biaya", "Setelah dikurangi modal, Ani mendapat keuntungan Rp15.000."),
        VocabItem("Kerugian", "Keadaan ketika hasil usaha tidak menutupi biaya", "Menjaga kebersihan produk mencegah terjadinya kerugian."),
        VocabItem("Promosi", "Kegiatan memperkenalkan produk", "Rani membuat poster warna-warni sebagai promosi gelangnya."),
        VocabItem("Penjualan", "Kegiatan menjual barang atau jasa", "Penjualan es buah meningkat saat cuaca panas.")
    )

    val idiomList = listOf(
        IdiomItem("Banting tulang", "Bekerja keras.", "Pak Budi bekerja banting tulang untuk mengembangkan usahanya."),
        IdiomItem("Buah tangan", "Oleh-oleh.", "Ayah membawa buah tangan kue tradisional dari pasar."),
        IdiomItem("Gulung tikar", "Bangkrut atau berhenti menjalankan usaha.", "Toko yang tidak jujur lama-lama bisa gulung tikar."),
        IdiomItem("Tangan kanan", "Orang kepercayaan.", "Kak Dina menjadi tangan kanan ibu dalam mengelola warung."),
        IdiomItem("Naik daun", "Sedang terkenal atau populer.", "Usaha kerajinan gelang buatan Rani sedang naik daun.")
    )

    val interviewSteps = listOf(
        "Menentukan topik wawancara.",
        "Menentukan narasumber.",
        "Menyiapkan daftar pertanyaan.",
        "Mengajukan pertanyaan dengan sopan.",
        "Mencatat jawaban narasumber.",
        "Menyusun hasil wawancara menjadi laporan."
    )

    val interviewQuestions = listOf(
        "Siapa nama Bapak/Ibu?",
        "Sejak kapan Bapak/Ibu memulai usaha ini?",
        "Apa alasan Bapak/Ibu memilih usaha ini?",
        "Produk apa saja yang dijual?",
        "Bagaimana cara menarik perhatian pembeli?",
        "Apa kesulitan yang pernah dihadapi?",
        "Apa pesan Bapak/Ibu untuk orang yang ingin memulai usaha?"
    )

    val quizQuestions = listOf(
        QuizQuestion(
            number = 1,
            question = "Jika kamu memiliki uang Rp20.000 untuk memulai usaha, apa yang akan kamu lakukan?",
            options = listOf(
                QuizOption("A", "Menghabiskan uang untuk jajan."),
                QuizOption("B", "Membeli bahan untuk membuat produk yang bisa dijual."),
                QuizOption("C", "Meminjam uang lagi."),
                QuizOption("D", "Menyimpan uang tanpa rencana.")
            ),
            correctLetter = "B",
            explanation = "Tepat! Uang modal digunakan untuk membeli bahan agar dapat membuat produk yang bernilai jual."
        ),
        QuizQuestion(
            number = 2,
            question = "Jika daganganmu tidak laku, apa yang sebaiknya kamu lakukan?",
            options = listOf(
                QuizOption("A", "Menyerah dan berhenti berjualan."),
                QuizOption("B", "Menyalahkan pembeli."),
                QuizOption("C", "Memperbaiki kualitas produk dan mencoba lagi."),
                QuizOption("D", "Tidak peduli dengan dagangan.")
            ),
            correctLetter = "C",
            explanation = "Hebat! Wirausahawan yang pantang menyerah terus mengevaluasi dan memperbaiki kualitas produknya."
        ),
        QuizQuestion(
            number = 3,
            readingText = "Bacalah teks berikut!\nRani membuat dan menjual gelang warna-warni. Ia selalu bersikap ramah kepada pembeli dan membuat gelang dengan bentuk yang menarik.",
            question = "Apa ide pokok dari teks tersebut?",
            options = listOf(
                QuizOption("A", "Rani suka bermain gelang."),
                QuizOption("B", "Rani menjalankan usaha gelang dengan kreatif dan ramah."),
                QuizOption("C", "Rani membeli gelang di toko."),
                QuizOption("D", "Rani tidak suka berjualan.")
            ),
            correctLetter = "B",
            explanation = "Benar! Ide pokok merangkum keseluruhan isi paragraf tentang usaha kreatif dan keramahan Rani."
        ),
        QuizQuestion(
            number = 4,
            question = "Jika kamu menjadi seorang wirausahawan, sikap apa yang ingin kamu kembangkan dalam dirimu?",
            options = listOf(
                QuizOption("A", "Jujur dan bertanggung jawab."),
                QuizOption("B", "Kreatif dan pantang menyerah."),
                QuizOption("C", "Disiplin dan pekerja keras."),
                QuizOption("D", "Semua sikap tersebut.")
            ),
            correctLetter = "D",
            explanation = "Luar biasa! Pengusaha sukses memadukan kejujuran, tanggung jawab, kreativitas, disiplin, dan kerja keras."
        )
    )

    val idiomGameRounds = listOf(
        GameIdiomRound(
            promptIdiom = "Banting tulang",
            sentenceHint = "Pak Budi bekerja ___ agar toko kuenya berkembang.",
            choices = listOf("Bekerja keras", "Oleh-oleh", "Bangkrut"),
            correctMeaning = "Bekerja keras"
        ),
        GameIdiomRound(
            promptIdiom = "Buah tangan",
            sentenceHint = "Ibu membawakan ___ kue klepon untuk nenek.",
            choices = listOf("Orang kepercayaan", "Oleh-oleh", "Sedang terkenal"),
            correctMeaning = "Oleh-oleh"
        ),
        GameIdiomRound(
            promptIdiom = "Naik daun",
            sentenceHint = "Gelang manik-manik buatan Rani sedang ___ di sekolah.",
            choices = listOf("Sedang terkenal atau populer", "Berhenti usaha", "Bekerja keras"),
            correctMeaning = "Sedang terkenal atau populer"
        ),
        GameIdiomRound(
            promptIdiom = "Tangan kanan",
            sentenceHint = "Dina menjadi ___ kakaknya saat menjaga stan bazar.",
            choices = listOf("Oleh-oleh", "Orang kepercayaan", "Modal usaha"),
            correctMeaning = "Orang kepercayaan"
        ),
        GameIdiomRound(
            promptIdiom = "Gulung tikar",
            sentenceHint = "Usaha yang tidak dikelola dengan jujur berisiko ___.",
            choices = listOf("Bangkrut atau berhenti usaha", "Mendapat untung besar", "Sedang terkenal"),
            correctMeaning = "Bangkrut atau berhenti usaha"
        )
    )

    val bibliography = listOf(
        "Nukman, E. Y., & Setyowati, C. E. (2023). Bahasa Indonesia: Lihat Sekitar untuk SD/MI Kelas IV (Edisi Revisi). Jakarta: Kementerian Pendidikan, Kebudayaan, Riset, dan Teknologi.",
        "Rohmiyatun. (2022). Kurikulum Merdeka Bahasa Indonesia SD/MI Kelas IV. Jakarta: Bumi Aksara.",
        "Suryana. (2017). Kewirausahaan: Kiat dan Proses Menuju Sukses (Edisi Keempat). Jakarta: Salemba Empat.",
        "Badan Pengembangan dan Pembinaan Bahasa. Kamus Besar Bahasa Indonesia (KBBI) Daring. KBBI Online."
    )
}

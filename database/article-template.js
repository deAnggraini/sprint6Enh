const basic = {
    id: 1000,
    name: 'Basic Template',
    categori: [],
    desc: '<p>Pilih template ini jika template yang direkomendasikan kurang sesuai dengan atribut page yang akan Kamu buat.</p>',
    images: '/templates/basic.svg',
    content: [
        {
            id: 1001,
            title: 'Ketentuan [Nama Produk]',
            desc: 'Berisi aturan/kaidah/ketetapan/syarat/kriteria atas produk/aplikasi yang harus dipahami pembaca sebelum melakukan prosedur atas produk/aplikasi tersebut; dapat dituangkan dalam bentuk kalimat ataupun tabel.',
            params: ['[Nama Produk]'],
            children: []
        },
        {
            id: 1002,
            title: 'Prosedur [Nama Produk]',
            desc: 'Berisi proses/alur kerja/tahapan/cara kerja yang terkait atas suatu produk/aplikasi, biasanya dijelaskan dalam bentuk diagram alur.',
            params: ['[Nama Produk]'],
            children: []
        },
        {
            id: 1003,
            title: 'Formulir [Nama Produk]',
            desc: 'Berisi list-list formulir apa saja yang digunakan atas suatu produk/aplikasi.',
            params: ['[Nama Produk]'],
            children: []
        },
    ]
}

module.exports.basic_template = basic;
module.exports.templates = [basic];

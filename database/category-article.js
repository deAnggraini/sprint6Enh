const categories = [
    {
        id: 100,
        title: 'Produk Untuk Nasabah',
        level: 0,
        desc: '',
        sort: 0,
        menus: [
            {
                id: 110,
                title: 'Produk Dana',
                level: 1,
                desc: '',
                sort: 0,
                menus: [
                    {
                        id: 111,
                        title: 'Deposito',
                        level: 1,
                        desc: '',
                    },
                    {
                        id: 112,
                        title: 'Giro',
                        level: 1,
                        desc: '',
                    },
                    {
                        id: 113,
                        title: 'LAKU',
                        level: 1,
                        desc: '',
                    },
                    {
                        id: 114,
                        title: 'Simpanan Pelajar',
                        level: 1,
                        desc: '',
                    },
                    {
                        id: 115,
                        title: 'TabunganKu',
                        level: 1,
                        desc: '',
                    },
                    {
                        id: 116,
                        title: 'Tahapan Berjangka siMuda',
                        level: 1,
                        desc: '',
                    },
                ]
            },
            {
                id: 120,
                title: 'Produk Investasi & Asuransi',
                level: 1,
                desc: '',
                sort: 1,
                menus: [
                    {
                        id: 121,
                        title: 'Bancassurance',
                        level: 2,
                        desc: '',
                    },
                    {
                        id: 122,
                        title: 'DBMM',
                        level: 2,
                        desc: '',
                    },
                    {
                        id: 123,
                        title: 'Reksadana',
                        level: 2,
                        desc: '',
                    },
                    {
                        id: 124,
                        title: 'Obligasi Korporasi',
                        level: 2,
                        desc: '',
                    },
                    {
                        id: 125,
                        title: 'Obligasi Negara',
                        level: 2,
                        desc: '',
                        menus: [
                            {
                                id: 1251,
                                title: 'Obligasi Negara Ritel (ORI)',
                                level: 3,
                                desc: '',
                            },
                            {
                                id: 1252,
                                title: 'Obligasi Negara Valas (INDON)',
                                level: 3,
                                desc: '',
                            },
                            {
                                id: 1253,
                                title: 'Obligasi Negara Valas Syariah (INDOIS)',
                                level: 3,
                                desc: '',
                            },
                        ]
                    },
                    {
                        id: 126,
                        title: 'Sertifikat Berharga BI',
                        level: 2,
                        desc: '',
                        menus: [
                            {
                                id: 1261,
                                title: 'Surat Berharga BI dalam Valas (SBBI Valas)',
                                level: 3,
                                desc: '',
                            },
                            {
                                id: 1262,
                                title: 'Sertifikat Bank Indonesia (SBI)',
                                level: 3,
                                desc: '',
                            },
                        ]
                    }
                ]
            },
            {
                id: 130,
                title: 'Produk Digital',
                level: 1,
                desc: '',
                sort: 2,
            },
            {
                id: 140,
                title: 'Produk Kerja sama',
                level: 1,
                desc: '',
                sort: 3,
            },
            {
                id: 150,
                title: 'Produk Layanan Perbankan',
                level: 1,
                desc: '',
                sort: 4,
            },
            {
                id: 160,
                title: 'Produk Kartu Kredit',
                level: 1,
                desc: '',
                sort: 5,
            },
            {
                id: 170,
                title: 'Produk Kredit Kosumtif',
                level: 1,
                desc: '',
                sort: 6,
            },
            {
                id: 180,
                title: 'Produk Kredit Produktif',
                level: 1,
                desc: '',
                sort: 7,
            },
        ]
    },
    {
        id: 200,
        title: 'Aktivitas Cabang',
        level: 0,
        desc: '',
        sort: 1,
        menus: [
            {
                id: 210,
                title: 'Pengetahuan Perbankan',
                level: 1,
                desc: '',
                sort: 0,
            },
            {
                id: 220,
                title: 'Layanan Khusus di Cabang',
                level: 1,
                desc: '',
                sort: 1,
            },
            {
                id: 230,
                title: 'Pelaku cabang',
                level: 1,
                desc: '',
                sort: 2,
            },
            {
                id: 240,
                title: 'Aktivitas Harian di Cabang',
                level: 1,
                desc: '',
                sort: 3,
            },
            {
                id: 250,
                title: 'Pengelolaan nasabah',
                level: 1,
                desc: '',
                sort: 4,
            },
        ]
    },
    {
        id: 1,
        title: 'Aplikasi Mesin',
        level: 0,
        desc: '',
        sort: 2,
        menus: [
            {
                id: 2,
                title: 'Pendukung Transaksi Umum',
                level: 1,
                desc: '',
                sort: 0,
                menus: [
                    {
                        id: 121,
                        title: 'ABACAS',
                        level: 1,
                        desc: '',
                    },
                    {
                        id: 122,
                        title: 'Aplikasi customized report',
                        level: 1,
                        desc: '',
                    }
                ]
            },
            {
                id: 3,
                title: 'Pendukung Transaksi Internasional',
                level: 1,
                desc: '',
                sort: 1,
                menus: [
                    {
                        id: 131,
                        title: 'Andy',
                        level: 1,
                        desc: '',
                    },
                    {
                        id: 132,
                        title: 'BDS-OR',
                        level: 1,
                        desc: '',
                    }
                ]
            },
            {
                id: 330,
                title: 'Pendukung Transaksi Kredit',
                level: 1,
                desc: '',
                sort: 2,
            },
            {
                id: 340,
                title: 'Internal BCA',
                level: 1,
                desc: '',
                sort: 3,
            },
            {
                id: 350,
                title: 'Regulator',
                level: 1,
                desc: '',
                sort: 4,
            },
            {
                id: 360,
                title: 'Pendukung Operasional ',
                level: 1,
                desc: '',
                sort: 5,
            },
        ]
    },
    {
        id: 11,
        title: 'Others',
        level: 0,
        sort: 3,
    }
];

module.exports = categories;

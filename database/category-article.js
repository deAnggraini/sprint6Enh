const categories = [
    {
        id: 100,
        title: 'Produk Untuk Nasabah',
        level: 1,
        desc: 'Daftar operasional cabang yang dikategorikan berdasarkan tugasnya.',
        sort: 0,
        parent: 0,
        menus: [
            {
                id: 110,
                title: 'Produk Dana',
                level: 2,
                desc: '',
                sort: 0,
                parent: 100,
                menus: [
                ]
            },
            {
                id: 120,
                title: 'Produk Investasi & Asuransi',
                level: 2,
                desc: '',
                sort: 1,
                parent: 100,
                menus: [
                    {
                        id: 125,
                        title: 'Obligasi Negara',
                        level: 3,
                        desc: '',
                        parent: 120,
                        menus: [
                        ]
                    },
                    {
                        id: 126,
                        title: 'Sertifikat Berharga BI',
                        level: 3,
                        desc: '',
                        parent: 120,
                        menus: [
                        ]
                    }
                ]
            },
            {
                id: 130,
                title: 'Produk Digital',
                level: 2,
                desc: '',
                sort: 2,
                parent: 100,
            },
            {
                id: 140,
                title: 'Produk Kerja sama',
                level: 2,
                desc: '',
                sort: 3,
                parent: 100,
                menus: [
                    {
                        id: 141,
                        title: 'Kerja Sama Copart',
                        level: 3,
                        desc: '',
                        parent: 140,
                        menus: [
                        ]
                    },
                    {
                        id: 142,
                        title: 'Kerja Sama Merchant',
                        level: 3,
                        desc: '',
                        parent: 140,
                        menus: [
                        ]
                    },
                    {
                        id: 143,
                        title: 'Kerja sama Institusi',
                        level: 3,
                        desc: '',
                        parent: 140,
                        menus: [
                        ]
                    },
                    {
                        id: 144,
                        title: 'Kerja sama perusahaan anak',
                        level: 3,
                        desc: '',
                        parent: 140,
                        menus: [
                        ]
                    },
                    {
                        id: 145,
                        title: 'Kerja sama perusahaan khusus',
                        level: 3,
                        desc: '',
                        parent: 140,
                        menus: [
                        ]
                    },
                ]
            },
            {
                id: 150,
                title: 'Produk Layanan Perbankan',
                level: 2,
                desc: '',
                sort: 4,
                parent: 100,
                menus: [
                    {
                        id: 151,
                        title: 'Perbankan Internasional',
                        level: 3,
                        desc: '',
                        parent: 150,
                        menus: [
                        ]
                    },
                    {
                        id: 152,
                        title: 'Treasuri',
                        level: 3,
                        desc: '',
                        parent: 150,
                        menus: [
                        ]
                    },
                    {
                        id: 153,
                        title: 'Perbankan Domesik',
                        level: 3,
                        desc: '',
                        parent: 150,
                        menus: [
                        ]
                    },
                ]
            },
            {
                id: 160,
                title: 'Produk Kartu Kredit',
                level: 2,
                desc: '',
                sort: 5,
                parent: 100,
            },
            {
                id: 170,
                title: 'Produk Kredit Konsumtif',
                level: 2,
                desc: '',
                sort: 6,
                parent: 100,
            },
            {
                id: 180,
                title: 'Produk Kredit Produktif',
                level: 2,
                desc: '',
                sort: 7,
                parent: 100,
                menus: [
                    {
                        id: 181,
                        title: 'SME',
                        level: 3,
                        desc: '',
                        parent: 180,
                        menus: [
                        ]
                    },
                    {
                        id: 182,
                        title: 'Komersial',
                        level: 3,
                        desc: '',
                        parent: 180,
                        menus: [
                        ]
                    },
                    {
                        id: 183,
                        title: 'Korporasi',
                        level: 3,
                        desc: '',
                        parent: 180,
                        menus: [
                        ]
                    },
                ]
            },
        ],
        icon: '/menus/coin.svg',
        image: '/struktur/100.svg',
        uri: null,
        edit: true,
    },
    {
        id: 200,
        title: 'Aktivitas Cabang',
        level: 1,
        desc: 'Produk BCA yang digunakan langsung oleh nasabah seperti tabungan e-banking dll.',
        sort: 1,
        parent: 0,
        menus: [
            {
                id: 210,
                title: 'Pengetahuan Perbankan',
                level: 2,
                desc: '',
                sort: 0,
                parent: 200,
            },
            {
                id: 220,
                title: 'Layanan Khusus di Cabang',
                level: 2,
                desc: '',
                sort: 1,
                parent: 200,
            },
            {
                id: 230,
                title: 'Pelaku cabang',
                level: 2,
                desc: '',
                sort: 2,
                parent: 200,
                menus: [
                    {
                        id: 231,
                        title: 'CS',
                        level: 3,
                        desc: '',
                        sort: 1,
                        parent: 230,
                        menus: [
                            {
                                id: 239,
                                title: 'Pembukaan rekening',
                                level: 4,
                                desc: '',
                                sort: 1,
                                parent: 231,
                            },
                            {
                                id: 238,
                                title: 'Perubahan Data',
                                level: 4,
                                desc: '',
                                sort: 1,
                                parent: 231,
                            },
                        ]
                    },
                    {
                        id: 232,
                        title: 'Teller',
                        level: 3,
                        desc: '',
                        sort: 1,
                        parent: 230,
                    },
                    {
                        id: 233,
                        title: 'AO SME',
                        level: 3,
                        desc: '',
                        sort: 1,
                        parent: 230,
                    },
                    {
                        id: 234,
                        title: 'AO Komersial',
                        level: 3,
                        desc: '',
                        sort: 1,
                        parent: 230,
                    },
                    {
                        id: 235,
                        title: 'RO',
                        level: 3,
                        desc: '',
                        sort: 1,
                        parent: 230,
                    },
                ]
            },
            {
                id: 240,
                title: 'Aktivitas Harian di Cabang',
                level: 2,
                desc: '',
                sort: 3,
                parent: 200,
            },
            {
                id: 250,
                title: 'Pengelolaan nasabah',
                level: 2,
                desc: '',
                sort: 4,
                parent: 200,
            },
        ],
        icon: '/menus/content.svg',
        image: '/struktur/200.svg',
        uri: null,
        edit: true,
    },
    {
        id: 1,
        title: 'Aplikasi/Mesin',
        level: 1,
        desc: 'Daftar operasional cabang yang dikategorikan berdasarkan tugasnya.',
        sort: 2,
        parent: 0,
        menus: [
            {
                id: 2,
                title: 'Pendukung Transaksi Umum',
                level: 2,
                desc: '',
                sort: 0,
                parent: 1,
                menus: [
                ]
            },
            {
                id: 3,
                title: 'Pendukung Transaksi Internasional',
                level: 2,
                desc: '',
                sort: 1,
                parent: 1,
                menus: [
                ]
            },
            {
                id: 330,
                title: 'Pendukung Transaksi Kredit',
                level: 2,
                desc: '',
                parent: 1,
                sort: 2,
            },
            {
                id: 340,
                title: 'Internal BCA',
                level: 2,
                desc: '',
                parent: 1,
                sort: 3,
            },
            {
                id: 350,
                title: 'Regulator',
                level: 2,
                desc: '',
                sort: 4,
                parent: 1,
            },
            {
                id: 360,
                title: 'Pendukung Operasional ',
                level: 2,
                desc: '',
                sort: 5,
                parent: 1,
            },
        ],
        icon: '/menus/mypage.svg',
        image: '/struktur/300.svg',
        uri: null,
        edit: true,
    }
];

module.exports = categories;

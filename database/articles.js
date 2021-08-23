const sample_empty = {
    id: 100,
    title: 'Tahapan',
    desc: '',
    image: '',
    video: '',
    created_at: new Date(),
    created_by: '',
    structureId: 2,
    structureParentList: [
        { id: 100, title: 'Produk Untuk Nasabah' },
        { id: 120, title: 'Produk Investasi & Asuransi' },
        { id: 126, title: 'Sertifikat Berharga BI' },
    ],
    contents: [
    ],
    references: [], // {id, title, no},
    related: [],  // {id, title}
    suggestions: [], // {id, title}
    isEmptyTemplate: true,
    isNew: true,
    isPublished: false,
};

const sample_basic = {
    id: 100,
    title: 'Tahapan',
    desc: `<p><b>TEBAL</b> <i>MIRING</i></p>`,
    image: 'a.jpg',
    video: '',
    created_at: new Date(),
    created_by: '',
    structureId: 2,
    structureParentList: [
        { id: 100, title: 'Produk Untuk Nasabah' },
        { id: 110, title: 'Produk Dana' },
    ],
    contents: [
        {
            id: 1,
            title: 'Ketentuan Tahapan',
            intro: '<p><b>Test bold</b></p>',
            parent: 0,
            level: 1,
            sort: 1,
            children: [],
        },
        {
            id: 2,
            title: 'Prosedur Tahapan',
            intro: '<p><i>Test Italic</i></p>',
            parent: 0,
            level: 1,
            sort: 2,
            children: [],
        },
        {
            id: 3,
            title: 'Formulir Tahapan',
            intro: '<p><u>Test Strike</u></p>',
            parent: 0,
            level: 1,
            sort: 2,
            children: [],
        }
    ],
    references: [
        { id: 2, title: "Perihal Ketentuan Tahapan 1", no: "025/SKSE/TL/2020" }
    ],
    related: [
        { id: 2, title: "Perihal Ketentuan Tahapan 1", no: "025/SKSE/TL/2020" }
    ],
    suggestions: [
        { id: 2, title: "Perihal Ketentuan Tahapan 1", no: "025/SKSE/TL/2020" }
    ],
    isEmptyTemplate: false,
    isNew: true,
    isPublished: false,
};

const sample_non_basic = {
    id: 101,
    title: 'Tahapan 2',
    structureId: 2,
    desc: '<p>H Q </p>',
    image: '/articles/tapres.jpeg',
    video: 'https://www.youtube.com/embed/oDZ-mIg2-Dk',
    // video: null,
    created_at: new Date(),
    created_by: '',
    location: 2,
    structureParentList: [
        { id: 100, title: 'Produk Untuk Nasabah' },
        { id: 110, title: 'Produk Dana' },
    ],
    contents: [
        {
            id: 1,
            title: 'Ketentuan Tahapan 2',
            intro: '',
            topicContent: '<p><strong>bold</strong></p><p><i>miring</i></p>',
            parent: 0,
            level: 1,
            sort: 1,
            children: [
                {
                    id: 3,
                    title: 'Rekening Tahapan 2',
                    topicTitle: 'Rekening Tahapan 2',
                    topicContent: '<p>test isi</>',
                    parent: 1,
                    level: 2,
                    sort: 1,
                    children: [
                        {
                            id: 18,
                            title: 'Satu',
                            topicTitle: '',
                            topicContent: '',
                            parent: 3,
                            level: 3,
                            sort: 1,
                            children: [
                                {
                                    id: 181,
                                    title: 'Satu titik satu',
                                    desc: '',
                                    parent: 18,
                                    level: 4,
                                    sort: 1,
                                    children: [
                                        {
                                            id: 1811,
                                            title: 'Terkahir minus satu',
                                            desc: '',
                                            parent: 181,
                                            level: 5,
                                            sort: 1,
                                            children: [],
                                        },
                                        {
                                            id: 1812,
                                            title: 'Terkahir minus 2',
                                            desc: '',
                                            parent: 181,
                                            level: 5,
                                            sort: 2,
                                            children: [],
                                        },
                                    ],
                                },
                                {
                                    id: 182,
                                    title: 'Satu titik dua',
                                    desc: '',
                                    parent: 18,
                                    level: 4,
                                    sort: 2,
                                    children: [],
                                }
                            ],
                        },
                        {
                            id: 19,
                            title: 'Dua',
                            topicTitle: '',
                            topicContent: '',
                            parent: 3,
                            level: 3,
                            sort: 2,
                            children: [],
                        },
                        {
                            id: 20,
                            title: 'Tiga',
                            topicTitle: '',
                            topicContent: '',
                            parent: 3,
                            level: 3,
                            sort: 3,
                            children: [],
                        },
                    ],
                },
                {
                    id: 4,
                    title: 'Pembukaan Rekening Tahapan 2',
                    topicTitle: '',
                    topicContent: '',
                    parent: 1,
                    level: 2,
                    sort: 2,
                    children: [
                        {
                            id: 18,
                            title: 'Satu',
                            topicTitle: '',
                            topicContent: '',
                            parent: 3,
                            level: 3,
                            sort: 1,
                            children: [
                                {
                                    id: 181,
                                    title: 'Satu titik satu',
                                    topicTitle: '',
                                    topicContent: '',
                                    parent: 18,
                                    level: 4,
                                    sort: 1,
                                    children: [
                                        {
                                            id: 1811,
                                            title: 'Terkahir minus satu',
                                            topicTitle: '',
                                            topicContent: '',
                                            parent: 181,
                                            level: 5,
                                            sort: 1,
                                            children: [],
                                        },
                                        {
                                            id: 1812,
                                            title: 'Terkahir minus 2',
                                            topicTitle: '',
                                            topicContent: '',
                                            parent: 181,
                                            level: 5,
                                            sort: 2,
                                            children: [],
                                        },
                                    ],
                                },
                                {
                                    id: 182,
                                    title: 'Satu titik dua',
                                    topicTitle: '',
                                    topicContent: '',
                                    parent: 18,
                                    level: 4,
                                    sort: 2,
                                    children: [],
                                }
                            ],
                        },
                        {
                            id: 19,
                            title: 'Dua',
                            topicTitle: '',
                            topicContent: '',
                            parent: 3,
                            level: 3,
                            sort: 2,
                            children: [],
                        },
                    ],
                },
                {
                    id: 5,
                    title: 'Perubahan Data Rekening Tahapan 2',
                    topicTitle: '',
                    topicContent: '',
                    parent: 1,
                    level: 2,
                    sort: 3,
                    children: [],
                },
                {
                    id: 6,
                    title: 'Penutupan Tahapan 2',
                    topicTitle: '',
                    topicContent: '',
                    parent: 1,
                    level: 2,
                    sort: 4,
                    children: [],
                },
                {
                    id: 7,
                    title: 'Biaya Tahapan 2',
                    topicTitle: '',
                    topicContent: '',
                    parent: 1,
                    level: 2,
                    sort: 5,
                    children: [],
                },
            ],
        },
        {
            id: 2,
            title: 'Prosedur Tahapan 2',
            intro: '',
            parent: 0,
            level: 1,
            sort: 2,
            children: [
                {
                    id: 8,
                    title: 'Rekening Tahapan',
                    topicTitle: '',
                    topicContent: '',
                    parent: 2,
                    level: 2,
                    sort: 1,
                    children: [],
                },
                {
                    id: 9,
                    title: 'Pembukaan Rekening Tahapan',
                    topicTitle: '',
                    topicContent: '',
                    parent: 2,
                    level: 2,
                    sort: 2,
                    children: [],
                },
                {
                    id: 10,
                    title: 'Perubahan Data Rekening Tahapan',
                    topicTitle: '',
                    topicContent: '',
                    parent: 2,
                    level: 2,
                    sort: 3,
                    children: [],
                },
                {
                    id: 11,
                    title: 'Penutupan Tahapan',
                    topicTitle: '',
                    topicContent: '',
                    parent: 2,
                    level: 2,
                    sort: 4,
                    children: [],
                },
                {
                    id: 12,
                    title: 'Biaya Tahapan',
                    topicTitle: '',
                    topicContent: '',
                    parent: 2,
                    level: 2,
                    sort: 5,
                    children: [],
                },
            ],
        },
        {
            id: 31,
            title: 'Formulir Tahapan 2',
            intro: '',
            parent: 0,
            level: 1,
            sort: 2,
            children: [],
        }
    ],
    references: [
        { id: 20, title: "Perihal Ketentuan Tahapan 1", no: "025/SKSE/TL/2020" }
    ],
    related: [
        { id: 20, title: "Perihal Ketentuan Tahapan 1", no: "025/SKSE/TL/2020" }
    ],
    suggestions: [
        { id: 20, title: "Perihal Ketentuan Tahapan 1", no: "025/SKSE/TL/2020" }
    ],
    isEmptyTemplate: false,
    isNew: false,
    isPublished: false,
};

const articles = [
    // sample_basic,
    // sample_non_basic,
    {
        id: 1,
        type: 'article',
        category: [
            { id: 1, title: 'Produk Untuk Nasabah' },
            { id: 2, title: 'Produk Kredit' },
            { id: 3, title: 'Kredit Produktif' },
            { id: 4, title: 'SME' },
            { id: 5, title: 'Time Loan' },
        ],
        title: 'TAHAKA (Tahapan Berjangka)',
        short_desc: '',
        desc: 'Dalam melakukan pembukaan Tahaka beberapa dokumen penting yang harus dilengkapi antara lain adalah',
        created_by: 'saifulh',
        created_date: Date.parse('2011-10-10T14:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/tahapan-berjangka.JPG',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 2,
        type: 'article',
        category: [
            { id: 1, title: 'Produk Untuk Nasabah' },
            { id: 2, title: 'Produk Kredit' },
            { id: 3, title: 'Kredit Produktif' },
            { id: 4, title: 'SME' },
            { id: 5, title: 'Time Loan' },
        ],
        title: 'Time Loan - SME',
        short_desc: '',
        desc: 'Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.',
        created_by: 'saifulh',
        created_date: Date.parse('2012-09-16T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/time-loan.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 3,
        type: 'article',
        category: [
            { id: 1, title: 'Produk Untuk Nasabah' },
            { id: 2, title: 'Produk Kredit' },
            { id: 3, title: 'Kredit Produktif' },
            { id: 4, title: 'Komersial' },
            { id: 5, title: 'Time Loan - Komersial' },
        ],
        title: 'GIRO',
        short_desc: '',
        desc: "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.",
        created_by: 'saifulh',
        created_date: Date.parse('2012-09-16T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/giro.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 4,
        type: 'article',
        category: [
            { id: 1, title: 'Produk Untuk Nasabah' },
            { id: 2, title: 'Produk Kredit' },
            { id: 3, title: 'Kredit Produktif' },
            { id: 4, title: 'Komersial' },
            { id: 5, title: 'Time Loan - Korporasi' },
        ],
        title: 'Kredit Local SME',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2013-01-01T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/kredit-lokal-sme.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 5,
        type: 'article',
        category: [
            { id: 1, title: 'Produk Untuk Nasabah' },
            { id: 2, title: 'Produk Kredit' },
            { id: 3, title: 'Kredit Produktif' },
            { id: 4, title: 'Komersial' },
            { id: 5, title: 'Time Loan - Korporasi' },
        ],
        title: 'Tahapan',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2013-02-01T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/tahapan.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 6,
        type: 'pdf',
        category: [
            { id: 1, title: 'PAKAR PDF' },
            { id: 2, title: 'Ketentuan Pengajuan Time Loan' },
        ],
        title: 'Welma',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2013-03-01T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/welma.png',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 7,
        type: 'article',
        category: [
            { id: 1, title: 'Lorem Ipsum' },
            { id: 2, title: 'Lorem Ipsum' },
            { id: 3, title: 'Lorem Ipsum' },
        ],
        title: 'Deposito',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2020-08-22T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/deposito.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 8,
        type: 'pdf',
        category: [
            { id: 1, title: 'PAKAR PDF' },
            { id: 2, title: 'Ketentuan Kredit Local' },
        ],
        title: 'Simpanan Pelajar',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2020-07-19T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/simpanan-pelajar.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 9,
        type: 'article',
        category: [
            { id: 1, title: 'Lorem Ipsum' },
            { id: 2, title: 'Lorem Ipsum' },
            { id: 3, title: 'Lorem Ipsum' },
            { id: 3, title: 'Lorem Ipsum - Ipsum' },
        ],
        title: 'Tahapan Xpresi',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2020-11-30T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/tahapan-xpresi.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 10,
        type: 'article',
        category: [
            { id: 1, title: 'Produk Untuk Nasabah' },
            { id: 2, title: 'Produk Kredit' },
            { id: 3, title: 'Kredit Produktif' },
            { id: 4, title: 'Korporasi' },
        ],
        title: 'TabunganKu',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2021-01-11T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/tabunganku.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 11,
        type: 'article',
        category: [
            { id: 1, title: 'Aplikasi Mesin' },
            { id: 2, title: 'Pendukung Transaksi Umum' },
            { id: 3, title: 'ABACAS' },
        ],
        title: 'Tapres',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2021-01-21T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/tapres.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 12,
        type: 'article',
        category: [
            { id: 1, title: 'Aplikasi Mesin' },
            { id: 2, title: 'Pendukung Transaksi International' },
            { id: 3, title: 'Andy' },
        ],
        title: 'DUITT',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2021-01-30T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/duitt.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 13,
        type: 'faq',
        category: [
            { id: 1, title: 'FAQ' },
            { id: 2, title: 'FAQ Operasional' },
            { id: 3, title: 'Studi Kasus Time Loan - SME' },
        ],
        title: 'Studi Kasus Time Loan - SME',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2021-01-30T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/deposito.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 14,
        type: 'faq',
        category: [
            { id: 1, title: 'FAQ' },
            { id: 2, title: 'FAQ Operasional' },
            { id: 3, title: 'Studi Kasus Time Loan - Komersial' },
        ],
        title: 'Studi Kasus Time Loan - Komersial',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2021-01-30T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/kredit-lokal-sme.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
    {
        id: 15,
        type: 'faq',
        category: [
            { id: 1, title: 'FAQ' },
            { id: 2, title: 'Lorem Ipsum' },
            { id: 3, title: 'Lorem Ipsum' },
        ],
        title: 'FAQ Lorem Ipsum',
        short_desc: '',
        desc: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        created_by: 'saifulh',
        created_date: Date.parse('2021-01-30T10:48:00'),
        updating_by: '',
        updating_date: null,
        update_by: '',
        update_date: null,
        image: '/articles/duitt.jpeg',
        attactments: [
            {
                file: '',
            }
        ],
        histories: [
            {
                id: 1,
                change_by: '',
                change_date: '',
                changes: [{
                    id: 1,
                    desc: ''
                }]
            }
        ]
    },
]

const lastKeyword = [
    { id: 0, text: 'Ketentuan Time Loan' },
    { id: 0, text: 'Prosedur Giro' },
    { id: 0, text: 'Open Payment' },
    { id: 0, text: 'Pembukaan Rekening Tahapan' },
    { id: 0, text: 'Flazz' },
];

module.exports.mypages = [
    {
        type: 'article',
        id: 1,
        title: 'Host to Host ERP Integration',
        location: 'Aplikasi dan Mesin > Aplikasi/Mesin Pendukung Transaksi Nasabah > Aplikasi Mesin Pendukung Transaksi Umum',
        modified_date: new Date(),
        modified_by: 'Putri Faizatu',
        approved_date: new Date(),
        approved_by: 'Diandra Amanda',
        affective_date: new Date(),
        send_to: 'Yayopriyo Wibowo',
        current_by: 'Theresa Theodorus, Anita Rachmat',
        state: 'DRAFT',
        isNew: false,

    },
    {
        type: 'microinformation',
        id: 2,
        title: 'Pembukaan Rekening Join Account',
        location: 'Virtual Page > Rekening',
        modified_date: new Date(),
        modified_by: 'Yayopriyo Wibowo',
        approved_date: new Date(),
        approved_by: 'Diandra Amanda',
        affective_date: new Date(),
        send_to: 'Yayopriyo Wibowo',
        current_by: 'Theresa Theodorus, Natasha Debora',
        state: 'DRAFT',
        isNew: true
    },
    {
        type: 'formulir',
        id: 3,
        title: 'Formulir Pembukaan Rekening',
        location: 'Formulir > Formulir',
        modified_date: new Date(),
        modified_by: 'Anita Rachmat',
        approved_date: new Date(),
        approved_by: 'Diandra Amanda',
        affective_date: new Date(),
        send_to: 'Yayopriyo Wibowo',
        current_by: 'Firda Agustriyani',
        state: 'DRAFT',
        isNew: false
    },
    {
        type: 'atribut',
        id: 4,
        title: 'Ini Adalah Judul Atribut Page',
        location: 'Virtual Page > Lorem Ipsum Dolor sit Amet',
        modified_date: new Date(),
        modified_by: 'Putu Ayu Sruti Permata Sari',
        approved_date: new Date(),
        approved_by: 'Diandra Amanda',
        affective_date: new Date(),
        send_to: 'Yayopriyo Wibowo',
        current_by: 'Firda Agustriyani, Kemal Batubara, Indah Permata Sari, Andreas Herawan, Thomas Wibisono, John Doe, Amalia..',
        state: 'DRAFT',
        isNew: true
    },


    {
        type: 'article',
        id: 5,
        title: 'Host to Host ERP Integration',
        location: 'Aplikasi dan Mesin > Aplikasi/Mesin Pendukung Transaksi Nasabah > Aplikasi Mesin Pendukung Transaksi Umum',
        modified_date: new Date(),
        modified_by: 'Putri Faizatu',
        approved_date: new Date(),
        approved_by: 'Diandra Amanda',
        affective_date: new Date(),
        send_to: 'Yayopriyo Wibowo',
        current_by: 'Theresa Theodorus, Anita Rachmat',
        state: 'DRAFT',
        isNew: false,

    },
    {
        type: 'microinformation',
        id: 6,
        title: 'Pembukaan Rekening Join Account',
        location: 'Virtual Page > Rekening',
        modified_date: new Date(),
        modified_by: 'Yayopriyo Wibowo',
        approved_date: new Date(),
        approved_by: 'Diandra Amanda',
        affective_date: new Date(),
        send_to: 'Yayopriyo Wibowo',
        current_by: 'Theresa Theodorus, Natasha Debora',
        state: 'DRAFT',
        isNew: true
    },
    {
        type: 'formulir',
        id: 7,
        title: 'Formulir Pembukaan Rekening',
        location: 'Formulir > Formulir',
        modified_date: new Date(),
        modified_by: 'Anita Rachmat',
        approved_date: new Date(),
        approved_by: 'Diandra Amanda',
        affective_date: new Date(),
        send_to: 'Yayopriyo Wibowo',
        current_by: 'Firda Agustriyani',
        state: 'DRAFT',
        isNew: false
    },
    {
        type: 'atribut',
        id: 8,
        title: 'Ini Adalah Judul Atribut Page',
        location: 'Virtual Page > Lorem Ipsum Dolor sit Amet',
        modified_date: new Date(),
        modified_by: 'Putu Ayu Sruti Permata Sari',
        approved_date: new Date(),
        approved_by: 'Diandra Amanda',
        affective_date: new Date(),
        send_to: 'Yayopriyo Wibowo',
        current_by: 'Firda Agustriyani, Kemal Batubara, Indah Permata Sari, Andreas Herawan, Thomas Wibisono, John Doe, Amalia..',
        state: 'DRAFT',
        isNew: true
    }
]

module.exports.notification = {
    // total_notification: 99,
    total_unread: 50,
    total_read: 49,

    totalElements: 99,
    totalPages: 10,
    list: [
        {
            id: 1,
            status: 'informasi',
            type: 'Informasi',
            isRead: true,
            date: new Date(),
            by: '',
            title: 'PAKAR Info',
            desc: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
        },
        {
            id: 2,
            status: 'konflik',
            type: 'Artikel',
            isRead: false,
            date: new Date(),
            by: 'Thalia Sari Landi',
            title: 'Tahapan',
            desc: 'Artikel Tahapan yang sedang kamu ubah sudah rilis versi terbaru. Segera sesuaikan dengan versi terbaru.'
        },
        {
            id: 3,
            status: 'terima',
            type: 'Artikel',
            isRead: true,
            date: new Date(),
            by: 'Stacia Marella',
            title: 'Joint Account',
            desc: 'Mohon review atas pembuatan Virtual Page.' // sendNote
        },
        {
            id: 4,
            status: 'publikasi',
            type: 'Artikel',
            isRead: false,
            date: new Date(),
            by: 'Shinta Dewi',
            title: 'Time Loan',
            desc: 'Perubahan PAKAR Time Loan telah disetujui.'
        },
        {
            id: 5,
            status: 'edit',
            type: 'Artikel',
            isRead: true,
            date: new Date(),
            by: 'Ni Luh Gede Sri Fajaryani',
            title: 'BCA Mobile',
            desc: 'Terdapat editor baru yang melakukan perubahan pada artikel yang sama. Pastikan kamu melakukan perubahan di versi paling baru.'
        }
    ]
}

module.exports.suggestion = [
    {
        id: 1,
        parent: 'PAKAR',
        items: [
            { id: 1, title: 'Tahapan' },
            { id: 2, title: 'Tahapan Gold' },
            { id: 3, title: 'TAHAKA' },
            { id: 4, title: 'Xpresi' },
            { id: 5, title: 'Time Loan SME' },
        ]
    },
    {
        id: 2,
        parent: 'FAQ',
        items: [
            { id: 11, title: 'Bagaimana solusi ketika Teller melakukan input kode penalti pada nasabah' },
            { id: 12, title: 'Apakah bisa membuka Deposito dari Data RTGS Masuk?' },
            { id: 13, title: 'Apakah bisa membuka Deposito?' },
        ]
    },
];

module.exports.recommendation = JSON.parse(JSON.stringify(articles));
module.exports.news = JSON.parse(JSON.stringify(articles)).reverse();
module.exports.popular = JSON.parse(JSON.stringify(articles)).slice(5, 10);

module.exports.lastKeyword = lastKeyword;
module.exports.articles = articles;
module.exports.sample_basic = sample_basic;
module.exports.sample_non_basic = sample_non_basic;
module.exports.sample_empty = sample_empty;
